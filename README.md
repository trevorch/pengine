# 一、促销与订单的交互
1. 在促销规则界面定制好促销内容->生成促销公式
2. 从订单数据中抽取促销公式所需要的数据->生成促销参数（一般是：商品数量(c)、商品价格(p)）
3. 把促销公式和促销参数丢进ScriptEngine运算，看结果的真假
4. 根据第三步的结果来决定是否要对订单执行相应的减价动作

# 二、促销展示
## 2.1、 真正商品上显示促销信息
1. 系统定时遍历所有促销，建立<商品id-促销列表>的映射
2. 在拉取商品时，从第一步中的映射中根据商品ID匹配相关促销信息
3. 一个商品ID可能会匹配到多个有效促销，此时取最优惠的
> 也就是说促销服务需要提供一个根据商品ID查询促销信息的接口

## 2.2、套装促销展示
1. Promotion实例中提供获取此促销对应的的促销虚拟商品的接口，虚拟商品并不是真正的商品，它只是根据促销信息生成的类似于真正商品的条目信息，它的属性是真正商品属性的子集，可以兼容展示在真正商品展示的位置
2. 促销虚拟商品的详情是促销信息中的商品信息，且进入促销虚拟商品详情页面时，需要实时同步对应的真实商品的库存和价格

## 2.3、套装促销下单
只需要根据促销信息中配置的最小满足条件来生成订单即可，也可以提供给用户修改的空间，但不能低于最小条件


## 2.4、促销形式
1. 满减 &radic;
2. 满折 &radic;
3. 满送商品 &radic;
4. 满送券 &radic;
5. 买几送几<sup><sup><sup>例如：买2送1</sup></sup></sup> &radic; 其实可以归类为满送商品 
6. 打包固定价格 &radic;

注；以上的“满”可能指价格满，也可以是商品数量满


# 关于apidoc
## 不能发送json数据
#### 1. 编辑如下文件
C:\Users\Trevor\AppData\Roaming\npm\node_modules\apidoc\template\utils\send_sample_request.js<br>
把96行开始的以下代码：
```
// send AJAX request, catch success or error callback
var ajaxRequest = {
    url        : url,
    headers    : header,
    data       : param,
    type       : type.toUpperCase(),
    success    : displaySuccess,
    error      : displayError
};

$.ajax(ajaxRequest);
```

替换为以下代码：
```
// send AJAX request, catch success or error callback
if(param.RequestBody) {
param=param.RequestBody
console.log("JSON POST")
$.ajax({
        url: url,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(JSON.parse(param)),
        headers: header,
        type: type.toUpperCase(),
        success: displaySuccess,
        error: displayError
    });
}
else {
    var ajaxRequest = {
        url        : url,
        headers    : header,
        data       : param,
        type       : type.toUpperCase(),
        success    : displaySuccess,
        error      : displayError
    };

    $.ajax(ajaxRequest);
}

```
#### 2. 修改模板文件
C:\Users\Trevor\AppData\Roaming\npm\node_modules\apidoc\template\index.html<br>
把227行:
```
<input id="sample-request-param-field-{{field}}" type="text" placeholder="{{field}}" class="form-control sample-request-param" data-sample-request-param-name="{{field}}" data-sample-request-param-group="sample-request-param-{{@../index}}" {{#if optional}}data-sample-request-param-optional="true"{{/if}}>
```
修改为:
```
<textarea id="sample-request-param-field-{{field}}"  placeholder="{{field}}" class="form-control sample-request-param" data-sample-request-param-name="{{field}}" data-sample-request-param-group="sample-request-param-{{@../index}}" {{#if optional}}data-sample-request-param-optional="true"{{/if}}/>
```
#### 3. 在注释中使用RequestBody作为参数名
```
    /**
     * @api {post} /promotion/create 创建促销
     * @apiSampleRequest /promotion/create
     * @apiName create
     * @apiGroup promotion
     *
     * @apiParam {json} RequestBody
```


## 解决apidoc访问不了后台的问题
在Controller上使用注解@CrossOrigin
```
@CrossOrigin
@RestController
@RequestMapping("/promotion")
public class CreateController extends Base {
```

## 文档生成
```
npm install apidoc
apidoc -t template
```