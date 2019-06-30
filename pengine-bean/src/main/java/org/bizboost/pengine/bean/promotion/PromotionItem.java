package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class PromotionItem extends Clone<PromotionItem> implements Serializable {
    public PromotionItem(){}
    public PromotionItem(String id, String name, int count, BigDecimal price) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;
}
