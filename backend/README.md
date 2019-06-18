###   stp (swagger to postman)
*   解析 swagger api doc 来生成可以直接导入的 postman collection 文件

##  快捷入口

*   [fdc - uat 版本](http://39.106.151.167:8080/index/download?name=fdc&useHost=false)

*   [fdc - host 环境变量版本](http://39.106.151.167:8080/index/download?name=fdc)

*   [rbc - uat 版本](http://39.106.151.167:8080/index/download?name=rbc&useHost=false)

*   [rbc - host 环境变量版本](http://39.106.151.167:8080/index/download?name=rbc)

*   [openapi - uat 版本](http://39.106.151.167:8080/index/download?name=openapi&useHost=false)

*   [openapi - host 环境变量版本](http://39.106.151.167:8080/index/download?name=openapi)

*   [openapiv2 - uat 版本](http://39.106.151.167:8080/index/download?name=openapiv2&useHost=false)

*   [openapiv2 - host 环境变量版本](http://39.106.151.167:8080/index/download?name=openapiv2)

*   [[api-gateway-fdc] - uat 版本](http://39.106.151.167:8080/index/download?name=api-gateway-fdc&useHost=false)

*   [[api-gateway-fdc] - host 环境变量版本](http://39.106.151.167:8080/index/download?name=api-gateway-fdc)

*   [[api-gateway-rbc] - uat 版本](http://39.106.151.167:8080/index/download?name=api-gateway-rbc&useHost=false)

*   [[api-gateway-rbc] - host 环境变量版本](http://39.106.151.167:8080/index/download?name=api-gateway-rbc)

*   [[ahs-partner] - host 环境变量版本](http://39.106.151.167:8080/index/download?name=ahs-partner)
```javascript
// ahs-partner 的鉴权
// 所有接口的 Authorization 头的值都是 postman 的环境变量 {{authorization}} 
// 把如下代码贴到登陆接口的 Test 标签即可

pm.test("set environment variable authorization", function () {
    var jwtToken = responseHeaders["Token"];
    pm.environment.set("authorization", "Bearer " + jwtToken);
});
```

*   [[dubai 网关] - host 环境变量版本](http://39.106.151.167:8080/index/download?name=dubai)

*   [[duha 网关] - host 环境变量版本](http://39.106.151.167:8080/index/download?name=duha)

### TODO
-   [x] MULTIPART_FORM_DATA的支持
-   [ ] 序列化器的选择
-   [ ] 可以由一个具体的 swagger api 直接生成 feignClient 代码



