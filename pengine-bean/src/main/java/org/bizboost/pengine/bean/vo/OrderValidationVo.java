package org.bizboost.pengine.bean.vo;

import lombok.Data;
import org.bizboost.pengine.bean.trade.Order;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class OrderValidationVo {
    private String promotionId;
    private Order order;

}
