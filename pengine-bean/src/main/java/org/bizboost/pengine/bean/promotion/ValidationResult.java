package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.enums.PromClassEnum;
import org.bizboost.pengine.bean.trade.Order;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ValidationResult<T> implements Serializable {
    private boolean ok;
    private String msg;
    private BigDecimal resultPrice;
    private Promotion promotion;
    private Order order;
    private PromClassEnum promClass;// 促销类型
    private T attachment; // 促销附赠：优惠券或商品
    public BigDecimal getSaving(){
        return order.getTotalPrice().subtract(resultPrice);
    }
    public BigDecimal getSavingPercent(){
        BigDecimal totalPrice = order.getTotalPrice();
        return totalPrice.subtract(resultPrice)
                .divide(totalPrice,3,BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
