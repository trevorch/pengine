package org.bizboost.pengine.util;

import com.alibaba.fastjson.JSON;
import org.bizboost.pengine.bean.promotion.Promotion;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public class PromotionUtil {
    public final static Promotion getPromotion(String text){
        Promotion promotion = JSON.parseObject(text,Promotion.class);
        return promotion;
    }
}
