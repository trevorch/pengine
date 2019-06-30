package org.bizboost.pengine.bean.promotion;

import lombok.Data;

import java.util.Date;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Data
public class Coupon {
    private String no;
    private Date expiredDate;
    private String desc;
}
