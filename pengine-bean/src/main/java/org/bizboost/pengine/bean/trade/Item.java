package org.bizboost.pengine.bean.trade;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class Item extends Clone<Item> implements Serializable {
    public Item(){}
    public Item(String id, String name, int count, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    private String id;
    private String name;
    private int count;
    private BigDecimal price;
}
