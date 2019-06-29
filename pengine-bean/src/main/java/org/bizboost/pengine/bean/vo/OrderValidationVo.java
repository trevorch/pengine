package org.bizboost.pengine.bean.vo;

import lombok.Data;
import org.bizboost.pengine.bean.trade.Order;

@Data
public class OrderValidationVo {
    private String promotionId;
    private Order order;

}
