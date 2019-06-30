package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.promotion.ValidateResult;
import org.bizboost.pengine.bean.trade.Order;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.springframework.web.bind.annotation.*;

import static org.bizboost.pengine.bean.vo.JsonResp.build;

@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class OrderController extends Base{

    /**
     * @api {post} /promotion/best 4.最佳促销活动
     * @apiDescription
     * 获取订单的最优惠的促销活动
     * @apiVersion 0.2.0
     * @apiSampleRequest /promotion/best
     * @apiName bestResult
     * @apiGroup Promotion
     *
     * @apiParam {json} RequestBody
     *
     * @apiParamExample {json} 样例参数:
     *  {
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
     *   }
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
     * @apiErrorExample {json} 错误响应:
     * {
     *     "code": "Failure",
     *     "msg": "找不到符合条件的促销",
     *     "ok": false
     * }
     */
    @PostMapping("best")
    @ResponseBody
    public JsonResp bestResult(@RequestBody Order order){
        JsonResp resp = build(true);
        try {
            ValidateResult result = promotionService.bestResult(order,promotionService.list());
            if(result==null) throw new Exception("找不到符合条件的促销");
            resp.setMsg(result);
        } catch (Exception e) {
            resp = build(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }

}
