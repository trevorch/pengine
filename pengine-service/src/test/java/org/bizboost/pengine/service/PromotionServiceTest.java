package org.bizboost.pengine.service;

import com.alibaba.fastjson.JSON;
import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.promotion.ValidationResult;
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

import javax.script.ScriptException;
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
    public void singleValidate() throws ScriptException, PromotionInvalidException, IllegalActionFormat {
        Order order = orders.get(0);

        Promotion promotion1 = promotionCacheService.getByPromotionId("RED001");
        ValidationResult result1 = promotionService.validate(order,promotion1);
        log.info("{}",JSON.toJSONString(result1,true));
        Assert.assertTrue((result1.isOk()&&result1.getAttachment()==null)
                ||(!result1.isOk()&&result1.getAttachment()==null));

        Promotion promotion2 = promotionCacheService.getByPromotionId("DIS001");
        ValidationResult result2 = promotionService.validate(order,promotion2);
        log.info("{}",JSON.toJSONString(result2,true));
        Assert.assertTrue((result2.isOk()&&result2.getAttachment()==null)
                ||(!result2.isOk()&&result2.getAttachment()==null));

        Promotion promotion3 = promotionCacheService.getByPromotionId("DIS002");
        ValidationResult result3 = promotionService.validate(order,promotion3);
        log.info("{}",JSON.toJSONString(result3,true));
        Assert.assertTrue((result3.isOk()&&result3.getAttachment()==null)
                ||(!result3.isOk()&&result3.getAttachment()==null));

        Promotion promotion4 = promotionCacheService.getByPromotionId("COU001");
        ValidationResult result4 = promotionService.validate(order,promotion4);
        log.info("{}",JSON.toJSONString(result4,true));
        Assert.assertTrue((result4.isOk()&&result4.getAttachment()!=null)
                ||(!result4.isOk()&&result4.getAttachment()==null));

        Promotion promotion5 = promotionCacheService.getByPromotionId("GIV001");
        ValidationResult result5 = promotionService.validate(order,promotion5);
        log.info("{}",JSON.toJSONString(result5,true));
        Assert.assertTrue((result5.isOk()&&result5.getAttachment()!=null)
                ||(!result5.isOk()&&result5.getAttachment()==null));
    }

    @Test
    public void bestResult() {

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotionCacheService.getByPromotionId("RED001"));
        promotions.add(promotionCacheService.getByPromotionId("DIS002"));

        Order order = orders.get(0);

        ValidationResult result = promotionService.bestResult(order,promotions);
        log.info("最优惠：{}",result.getMsg());
        Assert.assertTrue(order.getTotalPrice().compareTo(result.getResultPrice())>0);
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
