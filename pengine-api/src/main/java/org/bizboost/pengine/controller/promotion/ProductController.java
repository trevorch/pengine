package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bizboost.pengine.bean.vo.JsonResp.build;

@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class ProductController extends Base{

    /**
     * @api {get} /promotion/get-by-prod/:productId 商品最前促销
     * @apiSampleRequest /promotion/get-by-prod/:productId
     * @apiName get-by-prod
     * @apiGroup promotion
     *
     * @apiParam {string} productId

     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "success",
     *     "msg": {
     *         "action": "((c1*p1*0.8)+c2*p2+c3*p3)*0.8",
     *         "close": 1572019200000,
     *         "desc": "康师傅满10支打5折且总价满100打八折",
     *         "id": "PID-002",
     *         "map": {
     *             "1": {
     *                 "id": "1D45497B1D9749A6AD9E36D2E763490E",
     *                 "name": "康师傅"
     *             },
     *             "2": {
     *                 "id": "9F225BE8EB81477CB2373EA8281A20C9",
     *                 "name": "黑人牙膏"
     *             },
     *             "3": {
     *                 "id": "85B2A6E48E2A419CBF23AC41AE467F97",
     *                 "name": "统一奶茶"
     *             }
     *         },
     *         "name": "满100打八折",
     *         "rule": "(c1>=10)&&(c1*p1+c2*p2+c3*p3) >= 100",
     *         "sequence": 2,
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
     * @apiSampleRequest /promotion/list-by-prod/:productId
     * @apiName list-by-prod
     * @apiGroup promotion
     *
     * @apiParam {string} productId

     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "success",
     *     "msg": {
     *         "action": "((c1*p1*0.8)+c2*p2+c3*p3)*0.8",
     *         "close": 1572019200000,
     *         "desc": "康师傅满10支打5折且总价满100打八折",
     *         "id": "PID-002",
     *         "map": {
     *             "1": {
     *                 "id": "1D45497B1D9749A6AD9E36D2E763490E",
     *                 "name": "康师傅"
     *             },
     *             "2": {
     *                 "id": "9F225BE8EB81477CB2373EA8281A20C9",
     *                 "name": "黑人牙膏"
     *             },
     *             "3": {
     *                 "id": "85B2A6E48E2A419CBF23AC41AE467F97",
     *                 "name": "统一奶茶"
     *             }
     *         },
     *         "name": "满100打八折",
     *         "rule": "(c1>=10)&&(c1*p1+c2*p2+c3*p3) >= 100",
     *         "sequence": 2,
     *         "start": 1561651200000
     *     },
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
}
