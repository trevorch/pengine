package org.bizboost.pengine.service;

import org.bizboost.pengine.bean.promotion.Promotion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PromotionCacheServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PromotionCacheServiceTest.class);

    @Autowired
    private PromotionCacheService promotionCacheService;

    @Test
    public void test() {
        List<Promotion> promotion = promotionCacheService.getByProductId("8139349");
        Assert.assertTrue(promotion.size()>0);
    }

}
