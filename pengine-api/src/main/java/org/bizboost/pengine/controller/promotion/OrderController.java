package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.promotion.ValidationResult;
import org.bizboost.pengine.bean.trade.Order;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class OrderController extends Base{

    /**
     * @api {post} /promotion/best 订单最佳促销
     * @apiSampleRequest /promotion/best
     * @apiName bestResult
     * @apiGroup promotion
     *
     * @apiParam {string} RequestBody
     *
     * @apiParamExample {json} 请求参数样例
     *    {
     *     "no":"76748839",
     *     "items":[
     *       {
     *         "id": "1D45497B1D9749A6AD9E36D2E763490E",
     *         "name": "康师傅",
     *         "count": 11,
     *         "price": 5
     *       },
     *       {
     *         "id": "9F225BE8EB81477CB2373EA8281A20C9",
     *         "name": "黑人牙膏",
     *         "count": 100,
     *         "price": 2
     *       },
     *       {
     *         "id": "85B2A6E48E2A419CBF23AC41AE467F97",
     *         "name": "统一奶茶",
     *         "count": 10,
     *         "price": 5
     *       }
     *     ]
     *   }
     *
     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "success",
     *     "msg": {
     *         "msg": "订单【76748839】总价【305】满足【康师傅满10支打5折且总价满100打八折】,最终价格【235.20000000000002】",
     *         "order": {
     *             "items": [
     *                 {
     *                     "count": 11,
     *                     "id": "1D45497B1D9749A6AD9E36D2E763490E",
     *                     "name": "康师傅",
     *                     "price": 5
     *                 },
     *                 {
     *                     "count": 100,
     *                     "id": "9F225BE8EB81477CB2373EA8281A20C9",
     *                     "name": "黑人牙膏",
     *                     "price": 2
     *                 },
     *                 {
     *                     "count": 10,
     *                     "id": "85B2A6E48E2A419CBF23AC41AE467F97",
     *                     "name": "统一奶茶",
     *                     "price": 5
     *                 }
     *             ],
     *             "no": "76748839",
     *             "totalPrice": 305
     *         },
     *         "promotion": {
     *             "action": "((c1*p1*0.8)+c2*p2+c3*p3)*0.8",
     *             "close": 1572019200000,
     *             "desc": "康师傅满10支打5折且总价满100打八折",
     *             "id": "PID-002",
     *             "map": {
     *                 "1": {
     *                     "id": "1D45497B1D9749A6AD9E36D2E763490E",
     *                     "name": "康师傅"
     *                 },
     *                 "2": {
     *                     "id": "9F225BE8EB81477CB2373EA8281A20C9",
     *                     "name": "黑人牙膏"
     *                 },
     *                 "3": {
     *                     "id": "85B2A6E48E2A419CBF23AC41AE467F97",
     *                     "name": "统一奶茶"
     *                 }
     *             },
     *             "name": "满100打八折",
     *             "rule": "(c1>=10)&&(c1*p1+c2*p2+c3*p3) >= 100",
     *             "sequence": 2,
     *             "start": 1561651200000
     *         },
     *         "resultPrice": 235.20000000000002
     *     },
     *     "ok": true
     * }
     *
     */
    @PostMapping("best")
    @ResponseBody
    public Object bestResult(@RequestBody Order order){
        ValidationResult results = promotionService.bestResult(order,promotionService.list());
        return JsonResp.build().setMsg(results);
    }

}
