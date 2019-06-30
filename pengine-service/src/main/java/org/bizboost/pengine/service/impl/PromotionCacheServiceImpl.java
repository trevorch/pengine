package org.bizboost.pengine.service.impl;


import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.service.I18nService;
import org.bizboost.pengine.service.PromotionCacheService;
import org.bizboost.pengine.util.FileTool;
import org.bizboost.pengine.util.PromotionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@Service
public class PromotionCacheServiceImpl implements PromotionCacheService {
    private static final Logger log = LoggerFactory.getLogger(PromotionCacheServiceImpl.class);

    @Value("${promotion.file-dir}")
    private String promotionFileDir;
    @Value("${promotion.file-prefix}")
    private String promotionFilePrefix;
    @Value("${promotion.file-suffix}")
    private String promotionFileSuffix;
    @Autowired
    private I18nService i18nService;

    // 商品ID-促销列表
    private static final Map<String,List<Promotion>> PRODUCTID_PROMOTIONS_MAP = new ConcurrentHashMap<>();
    // 促销ID-促销
    private static final Map<String,Promotion> PROMOTIONID_PROMOTION_MAP = new ConcurrentHashMap<>();

    @Override
    @PostConstruct
    public void reload() {
        Thread T = Thread.currentThread();

        List<Promotion> promotions = new ArrayList<>();

        // 从文件中加载促销信息
        File promotionDir = new File(promotionFileDir);
        if(promotionDir.exists()){
            File [] files = promotionDir.listFiles((dir, name) -> {
                if(name.startsWith(promotionFilePrefix)&&name.endsWith(promotionFileSuffix)) return true;
                return false;
            });

            Arrays.asList(files).forEach(file -> {
                try {
                    String content = FileTool.getContent(file);
                    Promotion promotion = PromotionUtil.getPromotion(content);
                    try {
                        promotion.validate();
                        promotions.add(promotion);
                    } catch (PromotionInvalidException e) {
                        log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber()-3,e.getMessage());
                    }
                } catch (IOException e) {
                    log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber()-9,e.getMessage());
                    e.printStackTrace();
                }
            });
        }

        // TODO: 从数据库加载


        // 对促销做映射
        promotions.forEach(promotion -> create(promotion));
    }

    @Override
    public List<Promotion> getByProductId(String productId) {

        List<Promotion> promotions = PRODUCTID_PROMOTIONS_MAP.get(productId);
        if (promotions==null) return Collections.EMPTY_LIST;
        return promotions;

    }

    @Override
    public Promotion getByPromotionId(String promotionId) {
        return PROMOTIONID_PROMOTION_MAP.get(promotionId);
    }

    /**
     * 重建商品与促销的映射
     */
    private void rebuildProductPromotionMap(){
        PRODUCTID_PROMOTIONS_MAP.clear();
        PROMOTIONID_PROMOTION_MAP.forEach((promotionId,prom)->{
            prom.getMap().forEach((index,item)->{
                List<Promotion> plist = PRODUCTID_PROMOTIONS_MAP.get(item.getId());
                if(plist==null) PRODUCTID_PROMOTIONS_MAP.put(item.getId(),(plist=new CopyOnWriteArrayList<>()));
                plist.add(prom);
            });
        });
    }

    @Override
    public Promotion create(Promotion promotion) {
        Thread T = Thread.currentThread();
        try {
            Promotion clone = promotion.deepClone();
            PROMOTIONID_PROMOTION_MAP.put(clone.getId(),clone);
            rebuildProductPromotionMap();
            // TODO: 持久化
        } catch (Exception e) {
            log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber()-5,e.getMessage());
            e.printStackTrace();
        }
        return promotion;
    }

    @Override
    public List<Promotion> list() {
        Thread T = Thread.currentThread();

        List<Promotion> promotions = new ArrayList<>();

        PROMOTIONID_PROMOTION_MAP.values().forEach(promotion -> {
            try {
                promotions.add(promotion.deepClone());
            } catch (Exception e) {
                log.error("MethodName[{}()],LineNumber[{}],ErrorMessage[{}]", T.getStackTrace()[1].getMethodName(),T.getStackTrace()[1].getLineNumber()-2,e.getMessage());
                e.printStackTrace();
            }
        });

        return promotions;
    }

    @Override
    public List<Promotion> listByProductId(String productId) {
        List<Promotion> promotions = PRODUCTID_PROMOTIONS_MAP.get(productId);
        if(promotions==null) return Collections.EMPTY_LIST;
        return promotions;
    }


    /**
     * 定时检查缓存中的促销是否有效
     */
    @Scheduled(cron="0/5 * * * * ? ")
    public void validate(){
        Thread T = Thread.currentThread();
        PROMOTIONID_PROMOTION_MAP.forEach((promId,prom)->{
            try {
                prom.validate();
            } catch (PromotionInvalidException e) {
                log.info("PromId[{}],Name[{}],Message[{}]",prom.getId(),prom.getName(),e.getMessage());
                PROMOTIONID_PROMOTION_MAP.remove(promId);
            }
        });

        PRODUCTID_PROMOTIONS_MAP.forEach((prodId,proms)->{
            List<Promotion> invalid = new ArrayList<>();
            proms.forEach(prom->{
                try {
                    prom.validate();
                } catch (PromotionInvalidException e) {
                    log.info("PromId[{}],Name[{}],Message[{}]",prom.getId(),prom.getName(),e.getMessage());
                    invalid.add(prom);
                }
            });
            proms.removeAll(invalid);
        });
    }
}
