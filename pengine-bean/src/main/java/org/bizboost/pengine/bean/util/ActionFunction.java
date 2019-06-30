package org.bizboost.pengine.bean.util;

import lombok.Data;
import org.bizboost.pengine.bean.enums.PromotionTypeEnum;

@Data
public class ActionFunction {
    private PromotionTypeEnum promotionType;
    private String function;
}
