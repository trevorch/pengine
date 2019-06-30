package org.bizboost.pengine.bean.trade;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class Item extends Clone<Item> implements Serializable {
    private String id;
    private String name;
    private int count;
    private BigDecimal price;

    public Item(){}
    public Item(String id, String name, int count, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public final static Item build(){
        return new Item();
    }

    public Item setId(String id){
        this.id=id;
        return this;
    }
    public Item setName(String name){
        this.name=name;
        return this;
    }
    public Item setCount(int count){
        this.count=count;
        return this;
    }
    public Item setPrice(BigDecimal price){
        this.price=price;
        return this;
    }
}
