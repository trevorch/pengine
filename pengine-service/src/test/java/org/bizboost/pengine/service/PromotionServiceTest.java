package org.bizboost.pengine.service;

import com.alibaba.fastjson.JSON;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.IllegalRuleFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.promotion.ValidateResult;
import org.bizboost.pengine.bean.promotion.VirtualProduct;
import org.bizboost.pengine.bean.trade.Item;
import org.bizboost.pengine.bean.trade.Order;
import org.bizboost.pengine.utils.SampleDataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PromotionServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PromotionServiceTest.class);

    List<Order> orders = SampleDataUtil.getOrderList();

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionCacheService promotionCacheService;

    public PromotionServiceTest() throws IOException {
    }

    @Test
    public void singleValidate() throws PromotionInvalidException, IllegalActionFormat, IllegalRuleFormat {
        Order order = orders.get(0);
        Promotion promotion;
        ValidateResult result;

        promotion = promotionCacheService.getByPromotionId("RED001");
        result = promotionService.validate(order,promotion);
        log.info("{}",JSON.toJSONString(result,true));
        Assert.assertTrue((result.isOk()&&result.getGifts().isEmpty())
                ||(!result.isOk()&&result.getGifts().isEmpty()));

        promotion = promotionCacheService.getByPromotionId("DIS001");
        result = promotionService.validate(order,promotion);
        log.info("{}",JSON.toJSONString(result,true));
        Assert.assertTrue((result.isOk()&&result.getGifts().isEmpty())
                ||(!result.isOk()&&result.getGifts().isEmpty()));

        promotion = promotionCacheService.getByPromotionId("DIS002");
        result = promotionService.validate(order,promotion);
        log.info("{}",JSON.toJSONString(result,true));
        Assert.assertTrue((result.isOk()&&result.getGifts().isEmpty())
                ||(!result.isOk()&&result.getGifts().isEmpty()));

        promotion = promotionCacheService.getByPromotionId("COU001");
        result = promotionService.validate(order,promotion);
        log.info("{}",JSON.toJSONString(result,true));
        Assert.assertTrue((result.isOk()&&!result.getGifts().isEmpty())
                ||(!result.isOk()&&result.getGifts().isEmpty()));

        promotion = promotionCacheService.getByPromotionId("GIV001");
        result = promotionService.validate(order,promotion);
        log.info("{}",JSON.toJSONString(result,true));
        Assert.assertTrue((result.isOk()&&!result.getGifts().isEmpty())
                ||(!result.isOk()&&result.getGifts().isEmpty()));

        promotion = promotionCacheService.getByPromotionId("GIV002");
        result = promotionService.validate(order,promotion);
        log.info("{}",JSON.toJSONString(result,true));
        Assert.assertTrue((result.isOk()&&!result.getGifts().isEmpty())
                ||(!result.isOk()&&result.getGifts().isEmpty()));
    }

    @Test
    public void bestResult() {

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotionCacheService.getByPromotionId("RED001"));
        promotions.add(promotionCacheService.getByPromotionId("DIS002"));

        Order order = orders.get(0);

        ValidateResult result = promotionService.bestResult(order,promotions);
        log.info("最优惠：{}",result.getMsg());
        Assert.assertTrue(order.getTotalPrice().compareTo(result.getFinalPrice())>0);
    }

    @Test
    public void getByProductId() {
        Order order = orders.get(0);
        Item item = order.getItems().get(0);

        Promotion promotion = promotionService.getByProductId(item.getId());

        log.info(JSON.toJSONString(promotion,true));

    }

    @Test
    public void getVirtualProduct() throws PromotionInvalidException {

        VirtualProduct product = promotionService.getVirtualProduct("RED001");


        log.info(JSON.toJSONString(product,true));

    }
}
