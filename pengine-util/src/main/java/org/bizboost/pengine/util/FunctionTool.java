package org.bizboost.pengine.util;

import com.sun.deploy.util.StringUtils;
import org.bizboost.pengine.bean.enums.PromClassEnum;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;

public class FunctionTool {
    public final static String genFuncFromAction(String action) throws IllegalActionFormat {
        action= StringUtils.trimWhitespace(action);
        if(action==null) throw new IllegalActionFormat(String.format("非法促销力度格式不能为空, 格式要求:'<促销类型> <力度>'",action));
        String [] parts = action.split(" ");
        if(parts.length!=2) throw new IllegalActionFormat(String.format("非法促销力度格式:%s, 格式要求:'<促销类型> <力度>'",action));
        String funcName = parts[0].trim().toLowerCase(), returnVal = parts[1].trim();
        try {
            PromClassEnum.valueOf(funcName);
        }catch (Exception e){
            throw new IllegalActionFormat(String.format("不存在名为[%s]的促销类型",funcName));
        }

        String function = String.format("function %s(){return %s}",funcName,returnVal);
        return function;
    }
}
