package org.bizboost.pengine.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Locale;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class I18nServiceTest {
    private static final Logger log = LoggerFactory.getLogger(I18nServiceTest.class);

    @Autowired
    private I18nService i18nService;
    @Value("${promotion.file-dir}")
    private String promotionFileDir;

    @Test
    public void test() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        String msg = i18nService.get("order.promotion.valid");
        Assert.assertTrue(msg.contains("Order"));

        LocaleContextHolder.setLocale(Locale.US);
        msg = i18nService.get("order.promotion.valid");
        Assert.assertTrue(msg.contains("Order"));

        LocaleContextHolder.setLocale(Locale.CHINESE);
        msg = i18nService.get("order.promotion.valid");
        Assert.assertTrue(msg.contains("订单"));

        String path = I18nServiceTest.class.getClassLoader().getResource("PromotionCase").getPath();
        File file = new File(path);
        File [] files = file.listFiles();
        log.info("{}",path);
    }
}
