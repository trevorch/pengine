package org.bizboost.pengine.bean.util;

import lombok.Data;
import org.bizboost.pengine.bean.enums.PromotionTypeEnum;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class ActionFunction {
    private PromotionTypeEnum promotionType;
    private String function;
}
