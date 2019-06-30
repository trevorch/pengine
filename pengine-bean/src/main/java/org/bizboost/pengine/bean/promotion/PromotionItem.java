package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;
import java.math.BigDecimal;


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
