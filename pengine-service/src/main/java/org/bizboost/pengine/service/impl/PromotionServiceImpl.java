package org.bizboost.pengine.service.impl;


import org.bizboost.pengine.bean.enums.PromotionTypeEnum;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.IllegalRuleFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.*;
import org.bizboost.pengine.bean.trade.Order;
import org.bizboost.pengine.bean.util.Action;
import org.bizboost.pengine.bean.util.ActionFunction;
import org.bizboost.pengine.service.I18nService;
import org.bizboost.pengine.service.PromotionCacheService;
import org.bizboost.pengine.service.PromotionService;
import org.bizboost.pengine.util.PromotionActionTool;
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

import static java.lang.String.format;

@Service
public class PromotionServiceImpl implements PromotionService {
    private static final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);

    private final static ScriptEngineManager manager = new ScriptEngineManager();

    @Autowired
    private I18nService i18nService;
    @Autowired
    private PromotionCacheService promotionCacheService;

    class PromotionActionResult{
        PromotionTypeEnum promotionType;
        String result;
    }

    /**
     * 取最优惠
     * @param order
     * @param promotions
     * @return
     */
    @Override
    public ValidateResult bestResult(Order order, List<Promotion> promotions){
        Thread T = Thread.currentThread();

        List<ValidateResult> validateResults = new ArrayList<>();
        promotions.forEach(promotion -> {
            try {
                validateResults.add(validate(order,promotion));
            } catch (PromotionInvalidException|IllegalRuleFormat|IllegalActionFormat e) {
                log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]",T.getStackTrace()[1].getMethodName(), T.getStackTrace()[1].getLineNumber()-2, format("%s(%s)",i18nService.get("script.syntax.incorrect"),promotion.getRule()));
                //e.printStackTrace();
            }
        });

        if (validateResults.size()==0) return null;
        if (validateResults.size()==1) return validateResults.get(0);

        Collections.sort(validateResults,(c1,c2)->{
            if (c1.getFinalPrice().compareTo(c2.getFinalPrice())>0) return 1;
            if (c1.getFinalPrice().compareTo(c2.getFinalPrice())<0) return -1;
            return 0;
        });

        return validateResults.get(0);
    }

    @Override
    public ValidateResult validate(Order order, Promotion promotion) throws PromotionInvalidException, IllegalRuleFormat, IllegalActionFormat {
        Thread T = Thread.currentThread();

        ValidateResult validateResult = new ValidateResult();
        validateResult.setPromotion(promotion);
        validateResult.setOrder(order);
        validateResult.setGifts(Collections.EMPTY_LIST);

        // 验证订单及活动有效性, 并对订单商品以活动商品为基准分类：活动外的、活动中的、缺少的
        ClassifiedResult classifiedResult = promotion.validate(order);
        if(classifiedResult.getLack().size()>0){
            throw new PromotionInvalidException(format("订单[%s]不满足促销活动[%s], 因为缺少活动商品[%s]",order.getNo(),promotion.getName(), classifiedResult.lackTips()));
        }

        if(classifiedResult.getExtra().size()>0) log.info(format("订单[%s]含有非促销活动商品[%s]",order.getNo(),classifiedResult.extraTips()));

        validateResult.setCommonPrice(classifiedResult.commonPriceSum());
        validateResult.setExtraPrice(classifiedResult.extraPriceSum());

        Map<String, PromotionItem> indexItemMap = promotion.getMap();
        Map<String,String> idIndexMap = new HashMap<>();
        indexItemMap.forEach((index,item)->idIndexMap.put(item.getId(),index));

        ScriptEngine validator = manager.getEngineByName("js");
        // 给规则里的变量设置值
        // 只针对活动商品进行促销优惠计算
        classifiedResult.getCommon().forEach(item -> {
            String index = idIndexMap.get(item.getId());
            validator.put("c"+index,item.getCount());
            validator.put("p"+index,item.getPrice());
        });

        Object valid;
        try {
            valid = validator.eval(promotion.getRule());
        } catch (ScriptException e) {
            log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(), T.getStackTrace()[1].getLineNumber()-2, format("%s(%s)",i18nService.get("script.syntax.incorrect"),promotion.getRule()));
            e.printStackTrace();
            throw new IllegalRuleFormat(format("促销规则有误[%s]",promotion.getRule()));
        }

        Action action = PromotionActionTool.convert(promotion.getAction());
        validateResult.setPromotionType(action.getPromotionType());
        if(valid instanceof Boolean){
            if((boolean)valid){
                validateResult.setOk(true);

                // 加载促销力度函数到验证引擎
                ActionFunction function = action.functionalize();
                try {
                    validator.eval(function.getFunction());
                } catch (ScriptException e) {
                    log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]",T.getStackTrace()[1].getMethodName(), T.getStackTrace()[1].getLineNumber()-2, format("%s(%s)",i18nService.get("script.syntax.incorrect"),function.getFunction()));
                    e.printStackTrace();
                    throw new IllegalActionFormat(format("促销力度格式有误[%s]",promotion.getAction()));
                }

                Invocable invoker = (Invocable)validator;
                PromotionActionResult promotionActionResult = new PromotionActionResult();
                // 看看当前促销属于哪种类型，把相关信息记录下来
                for (PromotionTypeEnum promotionType: PromotionTypeEnum.values()){
                    try {
                        promotionActionResult.result=invoker.invokeFunction(promotionType.name()).toString();
                        promotionActionResult.promotionType=promotionType;
                        break; // 匹配到了就立即退出循环
                    } catch (ScriptException e) {
                        log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber()-4, format("%s(%s)",i18nService.get("script.syntax.incorrect"),function.getFunction()));
                        e.printStackTrace();
                        throw new IllegalActionFormat(format("促销力度格式有误[%s]",promotion.getAction()));
                    } catch (NoSuchMethodException e) {
                        log.info("不是促销类型[{}]",promotionType.name());
                    }
                }

                switch (promotionActionResult.promotionType){
                    case reduce:
                    case discount:
                        // 满减和满折，需要把参与活动部分金额减去优惠 + 未参与活动部分金额，才是最终订单价格
                        BigDecimal reducedPrice=new BigDecimal(promotionActionResult.result);
                        validateResult.setReducedPrice(reducedPrice.setScale(2,BigDecimal.ROUND_UP));
                        BigDecimal finalPrice = validateResult.getExtraPrice().add(reducedPrice).setScale(2,BigDecimal.ROUND_UP);
                        validateResult.setFinalPrice(finalPrice);
                        break;
                    case giving:
                    case coupon:
                        List<Gift> gifts = PromotionActionTool.resolveGifts(action);
                        validateResult.setGifts(gifts);
                        // 赠送形式的活动订单总价格不会变
                        validateResult.setFinalPrice(order.getTotalPrice().setScale(2,BigDecimal.ROUND_UP));
                        break;
                }

                // 订单[%s],总价[%s],非活动[%s],活动[%s],满足[%s],最终价[%s]
                String msg = format(i18nService.get("order.promotion.valid"),order.getNo(),order.getTotalPrice(),classifiedResult.extraPriceSum(),classifiedResult.commonPriceSum(),promotion.getName(),validateResult.getFinalPrice());
                validateResult.setMsg(msg);
                log.info(msg);
            }else{
                validateResult.setOk(false);
                String msg = format(i18nService.get("order.promotion.invalid"),order.getNo(),promotion.getDesc());
                validateResult.setMsg(msg);
                validateResult.setFinalPrice(order.getTotalPrice());
                log.info(msg);
            }
        }

        return validateResult;
    }

    @Override
    public ValidateResult validate(String orderId, String promotionId) throws PromotionInvalidException, IllegalRuleFormat, IllegalActionFormat {

        // TODO: get order info according orderId
        Order order = null;

        Promotion promotion = getByPromotionId(promotionId);

        ValidateResult validateResult = validate(order,promotion);
        return validateResult;
    }

    /**
     * 根据商品ID取得一个优惠的促销信息
     * @param productId
     * @return
     */
    @Override
    public Promotion getByProductId(String productId) {
        Thread T = Thread.currentThread();

        Promotion promotion = null;

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
                log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber(),e.getMessage());
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
        Thread T = Thread.currentThread();

        Promotion promotion = promotionCacheService.getByPromotionId(promotionId);
        if(promotion==null) throw new PromotionInvalidException("促销[promotionId="+promotionId+"]不存在");

        promotion.validate();

        VirtualProduct product = new VirtualProduct();
        try {
            Promotion clone = promotion.deepClone();
            product.setName(clone.getName());
            product.setDesc(clone.getDesc());
            product.setId("PROM-"+clone.getId());
            List<PromotionItem> items = new ArrayList<>();
            clone.getMap().forEach((index,item)->items.add(item));
            product.setItems(items);
        } catch (Exception e) {
            int ln = Thread.currentThread().getStackTrace()[1].getLineNumber()-8;
            log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber(),e.getMessage());
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public Promotion create(Promotion promotion) {
        Thread T = Thread.currentThread();
        try {
            promotionCacheService.create(promotion.deepClone());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            int ln = Thread.currentThread().getStackTrace()[1].getLineNumber()-4;
            log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber(),e.getMessage());
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
