package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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
}
