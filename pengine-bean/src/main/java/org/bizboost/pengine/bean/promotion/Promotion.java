package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;
import org.bizboost.pengine.bean.exception.IllegalRuleFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.trade.Order;
import org.bizboost.pengine.bean.util.Rule;
import org.bizboost.pengine.util.PromotionTool;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class Promotion extends Clone<Promotion> implements Serializable {
    private String id;
    private String name;
    private String condition;
    private Rule rule;
    private String desc;
    private int sequence;
    private Date start;
    private Date close;
    private Map<String, PromotionItem> map;
    private String action;

    public void validate(boolean verifyRule) throws PromotionInvalidException {
        if(verifyRule){
            try {
                Rule rule = PromotionTool.convertToRule(condition);
                this.rule=rule;
            } catch (IllegalRuleFormat e) {
                throw new PromotionInvalidException(e.getMessage());
            }
        }
        if (start.getTime()>close.getTime()) throw new PromotionInvalidException("活动开始时间大于结束时间");
        long currentTime = System.currentTimeMillis();
        if (currentTime<start.getTime()) throw new PromotionInvalidException("活动尚未开始");
        if (currentTime>close.getTime()) throw new PromotionInvalidException("活动已经结束");
    }

    /**
     * 以促销活动商品为准，把订单商品分成三类：订单和活动都有(common)、订单有活动没有(extra)、活动有订单没有(lack)
     * @param order
     * @return
     * @throws PromotionInvalidException
     */
    public ClassifiedResult validate(Order order) throws PromotionInvalidException {
        Thread T = Thread.currentThread();
        validate(false);
        ClassifiedResult classifiedResult = new ClassifiedResult();
        Set<String> orderItemIdSet = new HashSet<>();
        order.getItems().forEach(item -> orderItemIdSet.add(item.getId()));
        // 把在订单中未出现的活动商品过滤出来
        Set<String> promotionItemIdSet = new HashSet<>();
        map.forEach((index,item)->{
            promotionItemIdSet.add(item.getId());
            if(!orderItemIdSet.contains(item.getId())) {
                try {
                    classifiedResult.getLack().add(item.deepClone());
                } catch (IOException|ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // 把订单中不在活动范围内的商品过滤出来, 订单和活动都有的也过滤出来
        order.getItems().forEach(item->{
            if (promotionItemIdSet.contains(item.getId())){
                try {
                    classifiedResult.getCommon().add(item.deepClone());
                } catch (IOException|ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    classifiedResult.getExtra().add(item.deepClone());
                } catch (IOException|ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        return classifiedResult;
    }
}
