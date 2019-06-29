package org.bizboost.pengine.service;


import org.bizboost.pengine.bean.exception.PromotionNotFoundException;
import org.bizboost.pengine.bean.promotion.Promotion;

import java.util.List;

public interface PromotionCacheService {
    void reload() throws PromotionNotFoundException;

    List<Promotion> getByProductId(String productId);
    Promotion getByPromotionId(String promotionId);

    Promotion create(Promotion promotion);

    List<Promotion> list();

    List<Promotion> listByProductId(String productId);
}
