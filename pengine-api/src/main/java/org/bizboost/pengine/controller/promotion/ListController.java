package org.bizboost.pengine.controller.promotion;

import org.bizboost.pengine.bean.promotion.Promotion;
import org.bizboost.pengine.bean.vo.JsonResp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bizboost.pengine.bean.vo.JsonResp.build;

@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class ListController extends Base{
    /**
     * @api {post} /promotion/list 2.促销活动列表
     * @apiDescription
     * 获取所有有效的促销活动，以列表形式返回
     * @apiVersion 0.2.0
     * @apiSampleRequest /promotion/list
     * @apiName list
     * @apiGroup Promotion
     *
     * @apiParamExample {json} 样例参数:
     *  无需参数
     *
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
     */
    @PostMapping("list")
    @ResponseBody
    public JsonResp list(){
        JsonResp resp = build(true);
        try {
            List<Promotion> promotions = promotionService.list();
            resp.setMsg(promotions);
        } catch (Exception e) {
            resp = build(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }

}
