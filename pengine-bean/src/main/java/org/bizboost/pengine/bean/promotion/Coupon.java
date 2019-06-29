package org.bizboost.pengine.bean.promotion;

import lombok.Data;

import java.util.Date;

@Data
public class Coupon {
    private String no;
    private Date expiredDate;
    private String desc;
}
