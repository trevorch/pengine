package org.bizboost.pengine.util;

import com.alibaba.fastjson.JSON;
import org.bizboost.pengine.bean.promotion.Promotion;

public class PromotionUtil {
    public final static Promotion getPromotion(String text){
        Promotion promotion = JSON.parseObject(text,Promotion.class);
        return promotion;
    }
}
