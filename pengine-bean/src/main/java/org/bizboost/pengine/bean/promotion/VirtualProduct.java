package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;
import java.util.List;

@Data
public class VirtualProduct extends Clone<VirtualProduct> implements Serializable {
    private String id;
    private String name;
    private String desc;
    private List<PromItem> items;
}
