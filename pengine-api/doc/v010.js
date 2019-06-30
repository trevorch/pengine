/**
 * @api {post} /promotion/create 创建促销
 * @apiVersion 0.1.0
 * @apiSampleRequest /promotion/create
 * @apiName create
 * @apiGroup Promotion
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
 *     "msg": "创建成功",
 *     "ok": true
 * }
 *
 */


/**
 * @api {post} /promotion/list 促销列表
 * @apiVersion 0.1.0
 * @apiSampleRequest /promotion/list
 * @apiName list
 * @apiGroup Promotion
 *
 * @apiParamExample {json} 请求参数样例:
 *  无需参数
 *
 * @apiSuccessExample {json} 成功响应:
 * {
 *     "code": "Success",
 *     "msg": [
 *         {
 *             "action": "discount ((c1*p1*0.5)+c2*p2+c3*p3)*0.6",
 *             "close": 1572019200000,
 *             "desc": "鲁花花生油满2瓶打5折且总价满100打6折",
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
 *             "rule": "(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 100",
 *             "sequence": 2,
 *             "start": 1561651200000
 *         }
 *     ],
 *     "ok": true
 * }
 */


/**
 * @api {post} /promotion/best 订单最佳促销
 * @apiVersion 0.1.0
 * @apiSampleRequest /promotion/best
 * @apiName bestResult
 * @apiGroup Promotion
 *
 * @apiParam {json} RequestBody
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
 *     "code": "Success",
 *     "msg": {
 *         "finalPrice": 2627.94,
 *         "gifts": [],
 *         "msg": "订单【98238179849】总价【7974.9】满足【鲁花花生油满2瓶打5折且总价满100打6折】,最终价格【2627.94】",
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
 *             "desc": "鲁花花生油满2瓶打5折且总价满100打6折",
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
 *             "rule": "(c1>=2)&&(c1*p1+c2*p2+c3*p3) >= 100",
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