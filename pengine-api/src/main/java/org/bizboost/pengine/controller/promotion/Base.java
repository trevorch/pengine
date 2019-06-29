package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class Base {

    @Autowired
    protected PromotionService promotionService;

}
