package org.bizboost.pengine.utils;

import com.alibaba.fastjson.JSON;
import org.bizboost.pengine.bean.trade.Order;
import org.bizboost.pengine.util.FileTool;

import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public class SampleDataUtil {
    public final static List<Order> getOrderList() throws IOException {
        String path = SampleDataUtil.class.getClassLoader().getResource("orders.json").getPath();
        File file = new File(path);
        return JSON.parseArray(FileTool.getContent(file),Order.class);
    }
}
