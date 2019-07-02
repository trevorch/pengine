package org.bizboost.pengine.bean.promotion;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.bizboost.pengine.bean.Clone;

import java.io.Serializable;

/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class Gift extends Clone<Gift> implements Serializable {
    private String id;
    private int count;

    public Gift(){}
    public Gift(String item){
        init(item);
    }
    public void init(String item){
        item= StringUtils.trim(item);
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
