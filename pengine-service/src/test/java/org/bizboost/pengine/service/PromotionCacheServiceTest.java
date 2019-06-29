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

@RunWith(SpringRunner.class)
@SpringBootTest
public class PromotionCacheServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PromotionCacheServiceTest.class);

    @Autowired
    private PromotionCacheService promotionCacheService;

    @Test
    public void test() {
        List<Promotion> promotion = promotionCacheService.getByProductId("1D45497B1D9749A6AD9E36D2E763490E");
        Assert.assertTrue(promotion.size()>0);
    }

}
