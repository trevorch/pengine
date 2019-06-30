package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.trade.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class ClassifiedResult {
    private List<PromotionItem> lack=new ArrayList<>();
    private List<Item> extra=new ArrayList<>();
    private List<Item> common=new ArrayList<>();

    public String lackTips(){
        StringBuilder sb = new StringBuilder();
        lack.forEach(item -> sb.append(item.getName()).append("(").append(item.getId()).append(") "));
        return sb.toString();
    }
    public String extraTips(){
        StringBuilder sb = new StringBuilder();
        extra.forEach(item -> sb.append(item.getName()).append("(").append(item.getId()).append(") "));
        return sb.toString();
    }

    public BigDecimal extraPriceSum(){
        BigDecimal priceSum = BigDecimal.ZERO;
        for(Item item:extra) priceSum=priceSum.add(item.getPrice().multiply(BigDecimal.valueOf(item.getCount())));
        return priceSum;
    }

    public BigDecimal commonPriceSum(){
        BigDecimal priceSum = BigDecimal.ZERO;
        for(Item item:common) priceSum=priceSum.add(item.getPrice().multiply(BigDecimal.valueOf(item.getCount())));
        return priceSum;
    }
}
