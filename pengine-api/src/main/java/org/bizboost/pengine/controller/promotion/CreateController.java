package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.springframework.web.bind.annotation.*;

import static org.bizboost.pengine.bean.vo.JsonResp.build;

@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class CreateController extends Base {

    /**
     * @api {post} /promotion/create 添加促销
     * @apiSampleRequest /promotion/create
     * @apiName create
     * @apiGroup promotion
     *
     * @apiParam {json} RequestBody
     *
     *  @apiParamExample {json} 请求参数样例:
     *  {
     *   "id": "DIS002",
     *   "name": "满1000打6折",
     *   "desc": "鲁花花生油满2瓶打5折且总价满1000打6折",
     *   "sequence": 2,
     *   "start": "2019-06-28 00:00:00",
     *   "close": "2019-10-26 00:00:00",
     *   "rule": "(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 1000",
     *   "action": "discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6",
     *   "map": {
     *     "1":{
     *       "id": "8139349",
     *       "name": "伊利金典有机纯牛奶"
     *     },
     *     "2":{
     *       "id": "1599047",
     *       "name": "鲁花花生油"
     *     },
     *     "3":{
     *       "id": "2828950",
     *       "name": "维达抽纸"
     *     }
     *   }
     * }
     *
     * @apiSuccessExample {json} 成功响应:
     * {
     *     "code": "success",
     *     "msg": "添加成功！",
     *     "ok": true
     * }
     *
     */
    @PostMapping("create")
    @ResponseBody
    public JsonResp create(@RequestBody Promotion promotion){
        JsonResp resp = build(true);
        try {
            promotionService.create(promotion);
        } catch (Exception e) {
            resp = build(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }

}
