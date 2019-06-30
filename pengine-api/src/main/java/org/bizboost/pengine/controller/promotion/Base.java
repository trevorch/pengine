package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public abstract class Base {

    @Autowired
    protected PromotionService promotionService;

}
