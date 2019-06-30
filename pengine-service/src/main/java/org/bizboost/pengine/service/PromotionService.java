package org.bizboost.pengine.service;



import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.IllegalRuleFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.promotion.ValidateResult;
import org.bizboost.pengine.bean.promotion.VirtualProduct;
import org.bizboost.pengine.bean.trade.Order;

import java.util.List;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
public interface PromotionService {
    ValidateResult bestResult(Order order, List<Promotion> promotions);
    ValidateResult validate(Order order, Promotion promotion) throws PromotionInvalidException, IllegalActionFormat, IllegalRuleFormat;
    ValidateResult validate(String orderId, String promotionId) throws PromotionInvalidException, IllegalActionFormat, IllegalRuleFormat;
    Promotion getByProductId(String pid);
    VirtualProduct getVirtualProduct(String promotionId) throws PromotionInvalidException;

    Promotion create(Promotion promotion);

    List<Promotion> list();

    Promotion getByPromotionId(String promotionId);

    List<Promotion> listByProductId(String productId);
}
