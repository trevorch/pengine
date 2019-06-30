package org.bizboost.pengine.bean.trade;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

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
public class Order extends Clone<Order> implements Serializable {
    private String no;
    private List<Item> items;
    public BigDecimal getTotalPrice(){
        BigDecimal total = BigDecimal.ZERO;
        for (Item item:items) total=total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getCount())));
        return total;
    }
}
