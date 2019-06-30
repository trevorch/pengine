package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;
import java.util.List;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class VirtualProduct extends Clone<VirtualProduct> implements Serializable {
    private String id;
    private String name;
    private String desc;
    private List<PromotionItem> items;
}
