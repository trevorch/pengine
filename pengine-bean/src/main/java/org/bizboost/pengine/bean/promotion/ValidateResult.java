package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.enums.PromotionTypeEnum;
import org.bizboost.pengine.bean.trade.Order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ValidateResult<T> implements Serializable {
    private boolean ok;
    private String msg;
    private BigDecimal finalPrice;
    private Promotion promotion;
    private Order order;
    private PromotionTypeEnum promotionType;// 促销类型
    private List<Gift> gifts; // 促销附赠：优惠券或商品
    public BigDecimal getSaving(){
        if(order==null) return BigDecimal.ZERO;
        return order.getTotalPrice().subtract(finalPrice);
    }
    public BigDecimal getSavingPercent(){
        if(order==null) return BigDecimal.ZERO;
        BigDecimal totalPrice = order.getTotalPrice();
        return totalPrice.subtract(finalPrice)
                .divide(totalPrice,3,BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100)).setScale(1,BigDecimal.ROUND_HALF_UP);
    }
}
