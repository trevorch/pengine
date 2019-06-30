package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.promotion.VirtualProduct;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bizboost.pengine.bean.vo.JsonResp.build;

@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class ProductController extends Base{

    /**
     * @api {get} /promotion/get-by-prod/:productId 取商品排序最前促销
     * @apiVersion 0.2.0
     * @apiSampleRequest /promotion/get-by-prod/:productId
     * @apiName get-by-prod
     * @apiGroup promotion
     *
     * @apiParam {string} productId

     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "Success",
     *     "msg": {
     *         "action": "giving 2828950:2",
     *         "close": 1572019200000,
     *         "desc": "满700送纸巾",
     *         "id": "GIV001",
     *         "map": {
     *             "1": {
     *                 "id": "8139349",
     *                 "name": "伊利金典有机纯牛奶"
     *             },
     *             "2": {
     *                 "id": "1599047",
     *                 "name": "鲁花花生油"
     *             },
     *             "3": {
     *                 "id": "2828950",
     *                 "name": "维达抽纸"
     *             }
     *         },
     *         "name": "满700送1提纸巾",
     *         "rule": "(c1*p1+c2*p2+c3*p3) >= 700",
     *         "sequence": 0,
     *         "start": 1561651200000
     *     },
     *     "ok": true
     * }
     *
     */
    @GetMapping("get-by-prod/{productId}")
    @ResponseBody
    public JsonResp getByProductId(@PathVariable String productId) {
        JsonResp resp = build(true);
        try {
            Promotion promotion = promotionService.getByProductId(productId);
            if (promotion==null) throw new Exception("此商品没有任何促销活动");
            resp.setMsg(promotion);
        } catch (Exception e) {
            resp = build(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }


    /**
     * @api {get} /promotion/list-by-prod/:productId 商品促销列表
     * @apiVersion 0.2.0
     * @apiSampleRequest /promotion/list-by-prod/:productId
     * @apiName list-by-prod
     * @apiGroup promotion
     *
     * @apiParam {string} productId

     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "Success",
     *     "msg": [
     *         {
     *             "action": "discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6",
     *             "close": 1572019200000,
     *             "desc": "鲁花花生油满2瓶打5折且总价满1000打6折",
     *             "id": "DIS002",
     *             "map": {
     *                 "1": {
     *                     "id": "8139349",
     *                     "name": "伊利金典有机纯牛奶"
     *                 },
     *                 "2": {
     *                     "id": "1599047",
     *                     "name": "鲁花花生油"
     *                 },
     *                 "3": {
     *                     "id": "2828950",
     *                     "name": "维达抽纸"
     *                 }
     *             },
     *             "name": "满1000打6折",
     *             "rule": "(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 1000",
     *             "sequence": 2,
     *             "start": 1561651200000
     *         }
     *     ],
     *     "ok": true
     * }
     *
     */
    @GetMapping("list-by-prod/{productId}")
    @ResponseBody
    public JsonResp listByProductId(@PathVariable String productId) {
        JsonResp resp = build(true);
        try {
            List<Promotion> promotions = promotionService.listByProductId(productId);
            resp.setMsg(promotions);
        } catch (Exception e) {
            resp = build(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }


    /**
     * @api {get} /promotion/virtual/:promotionId 取促销虚拟商品
     * @apiVersion 0.2.0
     * @apiSampleRequest /promotion/virtual/:promotionId
     * @apiName virtualProduct
     * @apiGroup promotion
     *
     * @apiParam {string} promotionId

     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "Success",
     *     "msg": {
     *         "desc": "满700送纸巾",
     *         "id": "PROM-GIV001",
     *         "items": [
     *             {
     *                 "id": "8139349",
     *                 "name": "伊利金典有机纯牛奶"
     *             },
     *             {
     *                 "id": "1599047",
     *                 "name": "鲁花花生油"
     *             },
     *             {
     *                 "id": "2828950",
     *                 "name": "维达抽纸"
     *             }
     *         ],
     *         "name": "满700送1提纸巾"
     *     },
     *     "ok": true
     * }
     *
     */
    @GetMapping("virtual/{promotionId}")
    @ResponseBody
    public JsonResp getVirtualProduct(@PathVariable String promotionId) {
        JsonResp resp = build(true);
        try {
            VirtualProduct product = promotionService.getVirtualProduct(promotionId);
            resp.setMsg(product);
        } catch (Exception e) {
            resp = build(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }
}
