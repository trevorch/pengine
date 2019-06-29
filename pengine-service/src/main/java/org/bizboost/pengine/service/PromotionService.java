package org.bizboost.pengine.service;



import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.promotion.ValidationResult;
import org.bizboost.pengine.bean.promotion.VirtualProduct;
import org.bizboost.pengine.bean.trade.Order;

import javax.script.ScriptException;
import java.util.List;

public interface PromotionService {
    ValidationResult bestResult(Order order, List<Promotion> promotions);
    ValidationResult validate(Order order, Promotion promotion) throws ScriptException, PromotionInvalidException, IllegalActionFormat;
    ValidationResult validate(String orderId, String promotionId) throws ScriptException, PromotionInvalidException;
    Promotion getByProductId(String pid);
    VirtualProduct getVirtualProduct(String promotionId) throws PromotionInvalidException;

    Promotion create(Promotion promotion);

    List<Promotion> list();

    Promotion getByPromotionId(String promotionId);

    List<Promotion> listByProductId(String productId);
}
