package org.bizboost.pengine.service.impl;


import org.bizboost.pengine.bean.enums.PromClassEnum;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.*;
import org.bizboost.pengine.bean.trade.Item;
import org.bizboost.pengine.bean.trade.Order;
import org.bizboost.pengine.service.I18nService;
import org.bizboost.pengine.service.PromotionCacheService;
import org.bizboost.pengine.service.PromotionService;
import org.bizboost.pengine.util.FunctionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);

    private final static ScriptEngineManager manager = new ScriptEngineManager();

    @Autowired
    private I18nService i18nService;
    @Autowired
    private PromotionCacheService promotionCacheService;

    /**
     * 取最优惠
     * @param order
     * @param promotions
     * @return
     */
    @Override
    public ValidationResult bestResult(Order order, List<Promotion> promotions){
        String mn = Thread.currentThread().getStackTrace()[1].getMethodName();

        List<ValidationResult> validationResults = new ArrayList<>();
        promotions.forEach(promotion -> {
            try {
                validationResults.add(validate(order,promotion));
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (PromotionInvalidException|IllegalActionFormat e) {
                int ln = Thread.currentThread().getStackTrace()[1].getLineNumber()-4;
                log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", mn,ln,e.getMessage());
            }
        });

        if (validationResults.size()==0) return null;
        if (validationResults.size()==1) return validationResults.get(0);

        Collections.sort(validationResults,(c1,c2)->{
            if (c1.getResultPrice().compareTo(c2.getResultPrice())>0) return 1;
            if (c1.getResultPrice().compareTo(c2.getResultPrice())<0) return -1;
            return 0;
        });

        return validationResults.get(0);
    }

    @Override
    public ValidationResult validate(Order order, Promotion promotion) throws ScriptException, PromotionInvalidException, IllegalActionFormat {
        String mn = Thread.currentThread().getStackTrace()[1].getMethodName();

        ValidationResult vr = new ValidationResult();
        vr.setPromotion(promotion);
        vr.setOrder(order);
        try {
            // 验证活动有效性
            promotion.validate();

            Map<String, PromItem> indexItemMap = promotion.getMap();

            Map<String,String> idIndexMap = new HashMap<>();
            indexItemMap.forEach((index,item)->idIndexMap.put(item.getId(),index));

            ScriptEngine engine = manager.getEngineByName("js");

            // 给规则里的变量设置值
            order.getItems().forEach(item -> {
                String index = idIndexMap.get(item.getId());
                engine.put("c"+index,item.getCount());
                engine.put("p"+index,item.getPrice());
            });
            engine.put("totalPrice",order.getTotalPrice());
            Object result = engine.eval(promotion.getRule());

            if(result instanceof Boolean){
                if((boolean)result){
                    vr.setOk(true);
                    String function = FunctionTool.genFuncFromAction(promotion.getAction());
                    engine.eval(function);

                    Invocable invocable = (Invocable)engine;

                    class InvokeResult{
                        PromClassEnum promClass;
                        String promResult;
                    }
                    InvokeResult ir = new InvokeResult();
                    // 遍历促销类型，判断当前促销的类型
                    for (PromClassEnum pce:PromClassEnum.values()){
                        try {
                            ir.promResult=invocable.invokeFunction(pce.name()).toString();
                            ir.promClass=pce;
                            break; // 匹配到了就立即退出循环
                        } catch (ScriptException e) {
                            int ln = Thread.currentThread().getStackTrace()[1].getLineNumber()-3;
                            log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", mn,ln,e.getMessage());
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            log.info("不是促销类型[{}]",pce.name());
                        }
                    }

                    if(ir.promClass==null){
                        throw new IllegalActionFormat(String.format("促销活动[%s]的促销类型力度[%s]设置有误！",promotion.getName(),promotion.getAction()));
                    }

                    String resultPrice = order.getTotalPrice().toString();

                    vr.setPromClass(ir.promClass);
                    switch (ir.promClass){
                        case reduce:
                        case discount:
                            // 满减和满折，把价格设置回验证结果即可
                            resultPrice=ir.promResult;
                            break;
                        case giving:
                            String productId = ir.promResult;
                            // 查询所赠送商品详情放入验证结果中，并把赠送品价格设置为0
                            Item product = new Item();
                            product.setId(productId);
                            product.setPrice(BigDecimal.ZERO);
                            product.setCount(1);
                            vr.setAttachment(product);
                            break;
                        case coupon:
                            // 查询优惠券信息并设置回验证结果中
                            String couponId = ir.promResult;
                            Coupon coupon = new Coupon();
                            coupon.setNo(couponId);
                            vr.setAttachment(coupon);
                            break;
                    }
                    String msg = String.format(i18nService.get("order.promotion.valid"),order.getNo(),order.getTotalPrice(),promotion.getDesc(),resultPrice);
                    vr.setMsg(msg);
                    vr.setResultPrice(new BigDecimal(resultPrice).setScale(2,BigDecimal.ROUND_UP));
                    log.info(msg);
                }else{
                    vr.setOk(false);
                    String msg = String.format(i18nService.get("order.promotion.invalid"),order.getNo(),promotion.getDesc());
                    vr.setMsg(msg);
                    vr.setResultPrice(order.getTotalPrice());
                    log.info(msg);
                }
            }
        } catch (PromotionInvalidException e) {
            throw e;
        }
        return vr;
    }

    @Override
    public ValidationResult validate(String orderId, String promotionId) throws ScriptException, PromotionInvalidException {
        return null;
    }

    /**
     * 根据商品ID取得一个优惠的促销信息
     * @param productId
     * @return
     */
    @Override
    public Promotion getByProductId(String productId) {
        String mn = Thread.currentThread().getStackTrace()[1].getMethodName();

        Promotion promotion = new Promotion();

        List<Promotion> promotions = promotionCacheService.getByProductId(productId);
        if(promotions.size()>0){
            // 根据排序属性排序
            Collections.sort(promotions,(c1,c2)->{
                if(c1.getSequence()>c2.getSequence()) return 1;
                if(c1.getSequence()<c2.getSequence()) return -1;
                return 0;
            });
            // 深度拷贝
            Promotion priority = promotions.get(0);
            try {
                promotion=priority.deepClone();
            } catch (Exception e) {
                int ln = Thread.currentThread().getStackTrace()[1].getLineNumber()-2;
                log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", mn,ln,e.getMessage());
                e.printStackTrace();
            }
        }

        return promotion;
    }

    /**
     * 根据促销ID取得一个促销虚拟商品
     * @param promotionId
     * @return
     */
    @Override
    public VirtualProduct getVirtualProduct(String promotionId) throws PromotionInvalidException {
        String mn = Thread.currentThread().getStackTrace()[1].getMethodName();

        Promotion promotion = promotionCacheService.getByPromotionId(promotionId);
        if(promotion==null) throw new PromotionInvalidException("促销[promotionId="+promotionId+"]不存在");

        promotion.validate();

        VirtualProduct product = new VirtualProduct();
        try {
            Promotion clone = promotion.deepClone();
            product.setName(clone.getName());
            product.setDesc(clone.getDesc());
            product.setId("PROM-"+clone.getId());
            List<PromItem> items = new ArrayList<>();
            clone.getMap().forEach((index,item)->items.add(item));
            product.setItems(items);
        } catch (Exception e) {
            int ln = Thread.currentThread().getStackTrace()[1].getLineNumber()-8;
            log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", mn,ln,e.getMessage());
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public Promotion create(Promotion promotion) {
        String mn = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            promotionCacheService.create(promotion.deepClone());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            int ln = Thread.currentThread().getStackTrace()[1].getLineNumber()-4;
            log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", mn,ln,e.getMessage());
            e.printStackTrace();
        }
        return promotion;
    }

    @Override
    public List<Promotion> list() {
        List<Promotion> promotions = promotionCacheService.list();
        return promotions;
    }

    @Override
    public Promotion getByPromotionId(String promotionId) {
        return promotionCacheService.getByPromotionId(promotionId);
    }

    @Override
    public List<Promotion> listByProductId(String productId) {
        return promotionCacheService.listByProductId(productId);
    }
}
