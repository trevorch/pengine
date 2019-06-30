package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.trade.Order;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Data
public class Promotion extends Clone<Promotion> implements Serializable {
    private String id;
    private String name;
    private String desc;
    private int sequence;
    private Date start;
    private Date close;
    private String rule;
    private Map<String, PromItem> map;
    private String action;

    public void validate() throws PromotionInvalidException {
        if (start.getTime()>close.getTime()) throw new PromotionInvalidException("活动开始时间大于结束时间");
        long currentTime = System.currentTimeMillis();
        if (currentTime<start.getTime()) throw new PromotionInvalidException("活动尚未开始");
        if (currentTime>close.getTime()) throw new PromotionInvalidException("活动已经结束");
    }

    public List<PromItem> validate(Order order) throws PromotionInvalidException {
        Thread T = Thread.currentThread();
        validate();
        Set<String> orderItemIdSet = new HashSet<>();
        order.getItems().forEach(item -> orderItemIdSet.add(item.getId()));
        List<PromItem> lackPromItems = new ArrayList<>();
        map.forEach((index,item)->{
            if(!orderItemIdSet.contains(item.getId())) {
                try {
                    lackPromItems.add(item.deepClone());
                } catch (IOException|ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return lackPromItems;
    }
}
