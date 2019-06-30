package org.bizboost.pengine.bean.util;

import lombok.Data;
import org.bizboost.pengine.bean.enums.PromotionTypeEnum;

import static java.lang.String.format;

@Data
public class Action {
    private PromotionTypeEnum promotionType;
    private String strength;

    public ActionFunction functionalize(){
        ActionFunction function = new ActionFunction();
        String quote = "";
        switch (promotionType){
            case giving:
            case coupon:
                quote="'";
        }
        String functionStr = format("function %s(){return %s%s%s}",promotionType.name(),quote,strength,quote);
        function.setPromotionType(promotionType);
        function.setFunction(functionStr);
        return function;
    }
}
