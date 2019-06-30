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
     * @api {post} /promotion/best 订单最佳促销
     * @apiSampleRequest /promotion/best
     * @apiName bestResult
     * @apiGroup promotion
     *
     * @apiParam {string} RequestBody
     *
     * @apiParamExample {json} 请求参数样例
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
