package org.bizboost.pengine.bean.vo;

import lombok.Data;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class JsonResp<T> {
    private boolean ok;
    private String code;
    private T msg;
    public JsonResp(){}
    public JsonResp(boolean ok, String code, T msg) {
        this.ok = ok;
        this.code = code;
        this.msg = msg;
    }

    public final static JsonResp build(boolean ok){
        if (ok) return new JsonResp(ok,"Success","{}");
        return new JsonResp(ok,"Failure","{}");
    }

    public JsonResp setOk(boolean ok) {
        this.ok=ok;
        return this;
    }
    public JsonResp setCode(String code) {
        this.code=code;
        return this;
    }
    public JsonResp setMsg(T msg) {
        this.msg=msg;
        return this;
    }
}
