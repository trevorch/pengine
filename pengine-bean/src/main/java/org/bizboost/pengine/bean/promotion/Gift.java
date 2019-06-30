package org.bizboost.pengine.bean.promotion;

import com.sun.deploy.util.StringUtils;
import lombok.Data;

@Data
public class Gift {
    private String id;
    private int count;

    public Gift(){}
    public Gift(String item){
        init(item);
    }
    public void init(String item){
        item= StringUtils.trimWhitespace(item);
        String[] parts = item.split(":");
        this.id=parts[0];
        this.count=1;
        if (parts.length==2){
            // 设置了数量，则取所设置数量
            this.count=Integer.valueOf(parts[1]);
        }
    }

    public final static Gift build(String item){
        Gift benefit = new Gift(item);
        return benefit;
    }

    public Gift setId(String id){
        this.id = id;
        return this;
    }
    public Gift setCount(int count){
        this.count = count;
        return this;
    }


}
