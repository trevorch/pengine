package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.springframework.web.bind.annotation.*;

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
     *   "id": "PID-005",
     *   "name": "满100打八折",
     *   "desc": "总价满100打八折",
     *   "sequence": 3,
     *   "start": "2019-06-28 00:00:00",
     *   "close": "2019-10-26 00:00:00",
     *   "rule": "(c1*p1+c2*p2+c3*p3) >= 100",
        "map": {
          "1":{
            "id": "1D45497B1D9749A6AD9E36D2E763490E",
            "name": "康师傅",
            "count": 11,
            "price": 5
          },
          "2":{
            "id": "9F225BE8EB81477CB2373EA8281A20C9",
            "name": "黑人牙膏",
            "count": 100,
            "price": 2
          },
          "3":{
            "id": "85B2A6E48E2A419CBF23AC41AE467F97",
            "name": "统一奶茶",
            "count": 10,
            "price": 5
          }
        },
     *   "action": "totalPrice*0.8"
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
        promotionService.create(promotion);
        return JsonResp.build().setMsg("添加成功！");
    }

}
