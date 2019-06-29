package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.exception.IllegalActionFormat;
import org.bizboost.pengine.bean.exception.PromotionInvalidException;
import org.bizboost.pengine.bean.promotion.ValidationResult;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.bizboost.pengine.bean.vo.OrderValidationVo;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class ValidateController extends Base {

    /**
     * @api {post} /promotion/validate 验证促销
     * @apiSampleRequest /promotion/validate
     * @apiName validate
     * @apiGroup promotion
     *
     * @apiParam {string} RequestBody
     *
     * @apiParamExample {json} 请求参数样例
     *  {
     * 	"order":  {
     *     "no":"76748839",
     *     "items":[
     *       {
     *         "id": "1D45497B1D9749A6AD9E36D2E763490E",
     *         "name": "康师傅",
     *         "count": 1,
     *         "price": 5
     *       },
     *       {
     *         "id": "9F225BE8EB81477CB2373EA8281A20C9",
     *         "name": "黑人牙膏",
     *         "count": 1,
     *         "price": 2
     *       },
     *       {
     *         "id": "85B2A6E48E2A419CBF23AC41AE467F97",
     *         "name": "统一奶茶",
     *         "count": 10,
     *         "price": 5
     *       }
     *     ]
     *   },
     *   "promotionId":"PID-001"
     * }
     *
     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "success",
     *     "msg": {
     *         "msg": "订单【76748839】不满足【总价满100打八折】",
     *         "order": {
     *             "items": [
     *                 {
     *                     "count": 1,
     *                     "id": "1D45497B1D9749A6AD9E36D2E763490E",
     *                     "name": "康师傅",
     *                     "price": 5
     *                 },
     *                 {
     *                     "count": 1,
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
     *             "totalPrice": 57
     *         },
     *         "promotion": {
     *             "action": "totalPrice*0.8",
     *             "close": 1572019200000,
     *             "desc": "总价满100打八折",
     *             "id": "PID-001",
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
     *             "rule": "(c1*p1+c2*p2+c3*p3) >= 100",
     *             "sequence": 3,
     *             "start": 1561651200000
     *         },
     *         "resultPrice": 57
     *     },
     *     "ok": true
     * }
     *
     */
    @PostMapping("validate")
    @ResponseBody
    public Object validate(@RequestBody OrderValidationVo ovv) throws ScriptException, PromotionInvalidException, IllegalActionFormat {
        ValidationResult results = promotionService.validate(ovv.getOrder(),promotionService.getByPromotionId(ovv.getPromotionId()));
        return JsonResp.build().setMsg(results);
    }

}
