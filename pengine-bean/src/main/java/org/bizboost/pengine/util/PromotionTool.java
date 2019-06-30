package org.bizboost.pengine.util;

import com.sun.deploy.util.StringUtils;
import org.bizboost.pengine.bean.enums.PromotionTypeEnum;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.IllegalRuleFormat;
import org.bizboost.pengine.bean.promotion.Gift;
import org.bizboost.pengine.bean.util.Action;
import org.bizboost.pengine.bean.util.ActionFunction;
import org.bizboost.pengine.bean.util.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public class PromotionTool {
    /**
     * 把促销力度字符串转换为促销动作对象
     * @param actionStr
     * @return
     * @throws IllegalActionFormat
     */
    public final static Action convertToAction(String actionStr) throws IllegalActionFormat {
        actionStr= StringUtils.trimWhitespace(actionStr);
        if(actionStr==null) throw new IllegalActionFormat(format("非法促销力度格式不能为空, 格式要求:'<促销类型> <力度>'",actionStr));
        int firstSpaceIndex = actionStr.indexOf(" ");
        if(firstSpaceIndex==-1||firstSpaceIndex==0) throw new IllegalActionFormat(format("非法促销力度格式:%s, 格式要求:'<促销类型> <力度>'",actionStr));
        String promotionTypeName = StringUtils.trimWhitespace(actionStr.substring(0,firstSpaceIndex));
        String strength = StringUtils.trimWhitespace(actionStr.substring(firstSpaceIndex)).replaceAll(" ","");
        Action action = new Action();
        try {
            PromotionTypeEnum promotionType = PromotionTypeEnum.valueOf(promotionTypeName);
            action.setPromotionType(promotionType);
            action.setStrength(strength);
        }catch (Exception e){
            throw new IllegalActionFormat(format("不存在名为[%s]的促销类型",promotionTypeName));
        }
        return action;
    }

    public final static ActionFunction functionalize(String actionStr) throws IllegalActionFormat {
        Action action = convertToAction(actionStr);
        return functionalize(action);
    }

    public final static ActionFunction functionalize(Action action) {
        String functionStr = format("function %s(){return '%s'}",action.getPromotionType().name(),action.getStrength());
        ActionFunction function = new ActionFunction();
        function.setPromotionType(action.getPromotionType());
        function.setFunction(functionStr);
        return function;
    }

    public final static List<Gift> resolveGifts(String actionStr) throws IllegalActionFormat {
        Action action = convertToAction(actionStr);
        return resolveGifts(action);
    }

    public final static List<Gift> resolveGifts(Action action) throws IllegalActionFormat {
        String strength = action.getStrength();
        StringUtils.trimWhitespace(strength);
        if(strength==null) return Collections.EMPTY_LIST;
        String[] items = strength.split(",");
        List<Gift> gifts = new ArrayList<>();
        Arrays.asList(items).forEach(item->gifts.add(Gift.build(item)));
        return gifts;
    }

    /**
     * 把促销条件字符串转换为促销规则对象
     * @param conditionStr
     * @return
     * @throws IllegalActionFormat
     */
    public final static Rule convertToRule(String conditionStr) throws IllegalRuleFormat {
        conditionStr= StringUtils.trimWhitespace(conditionStr);
        if(conditionStr==null) throw new IllegalRuleFormat(format("非法促销力度格式不能为空, 格式要求:'<促销类型> <力度>'",conditionStr));
        int firstSpaceIndex = conditionStr.indexOf(" ");
        if(firstSpaceIndex==-1||firstSpaceIndex==0) throw new IllegalRuleFormat(format("非法促销力度格式:%s, 格式要求:'<促销类型> <力度>'",conditionStr));
        String exactMatchStr = StringUtils.trimWhitespace(conditionStr.substring(0,firstSpaceIndex));
        String ruleStr = StringUtils.trimWhitespace(conditionStr.substring(firstSpaceIndex)).replaceAll(" ","");
        Rule rule = new Rule();
        Boolean exactMatch = Boolean.valueOf(exactMatchStr);
        rule.setExactMatch(exactMatch);
        rule.setExpression(ruleStr);
        return rule;
    }
}