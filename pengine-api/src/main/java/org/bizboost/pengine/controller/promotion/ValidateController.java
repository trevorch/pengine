package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.IllegalRuleFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.ValidateResult;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.bizboost.pengine.bean.vo.OrderValidationVo;
import org.springframework.web.bind.annotation.*;

import static org.bizboost.pengine.bean.vo.JsonResp.build;

@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class ValidateController extends Base {

    /**
     * @api {post} /promotion/validate 验证促销
     * @apiVersion 0.2.0
     * @apiSampleRequest /promotion/validate
     * @apiName validate
     * @apiGroup promotion
     *
     * @apiParam {string} RequestBody
     *
     * @apiParamExample {json} 请求参数样例
     *  {
     * 	"order":  {
     *     "no":"98238179849",
     *     "items":[
     *       {
     *         "id": "8139349",
     *         "name": "伊利金典有机纯牛奶",
     *         "count": 100,
     *         "price": 71.90
     *       },
     *       {
     *         "id": "1599047",
     *         "name": "鲁花花生油",
     *         "count": 1,
     *         "price": 165.90
     *       },
     *       {
     *         "id": "2828950",
     *         "name": "维达抽纸",
     *         "count": 10,
     *         "price": 61.90
     *       }
     *     ]
     *   },
     *   "promotionId":"DIS002"
     * }
     *
     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "Success",
     *     "msg": {
     *         "finalPrice": 2627.94,
     *         "gifts": [],
     *         "msg": "订单【98238179849】总价【7974.9】满足【鲁花花生油满2瓶打5折且总价满1000打6折】,最终价格【2627.94】",
     *         "ok": true,
     *         "order": {
     *             "items": [
     *                 {
     *                     "count": 100,
     *                     "id": "8139349",
     *                     "name": "伊利金典有机纯牛奶",
     *                     "price": 71.9
     *                 },
     *                 {
     *                     "count": 1,
     *                     "id": "1599047",
     *                     "name": "鲁花花生油",
     *                     "price": 165.9
     *                 },
     *                 {
     *                     "count": 10,
     *                     "id": "2828950",
     *                     "name": "维达抽纸",
     *                     "price": 61.9
     *                 }
     *             ],
     *             "no": "98238179849",
     *             "totalPrice": 7974.9
     *         },
     *         "promotion": {
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
     *         },
     *         "promotionType": "discount",
     *         "saving": 5346.96,
     *         "savingPercent": 67
     *     },
     *     "ok": true
     * }
     *
     */
    @PostMapping("validate")
    @ResponseBody
    public JsonResp validate(@RequestBody OrderValidationVo ovv) {
        JsonResp resp = build(true);
        try {
            ValidateResult results = promotionService.validate(ovv.getOrder(),promotionService.getByPromotionId(ovv.getPromotionId()));
            resp.setMsg(results);
        } catch (PromotionInvalidException| IllegalRuleFormat |IllegalActionFormat e) {
            resp = build(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }

}
