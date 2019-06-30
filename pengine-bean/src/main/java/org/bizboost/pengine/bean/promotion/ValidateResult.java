package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.enums.PromotionTypeEnum;
import org.bizboost.pengine.bean.trade.Order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class ValidateResult<T> implements Serializable {
    private boolean ok;
    private String msg;
    // 订单最终总价格
    private BigDecimal finalPrice;
    // 参与活动的总价格
    private BigDecimal commonPrice;
    // 参与活动的总价格减去优惠后的价格
    private BigDecimal afterPrice;
    // 不参与活动的总价格
    private BigDecimal extraPrice;
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
