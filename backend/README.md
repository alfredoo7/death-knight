###   stp (swagger to postman)
*   解析 swagger api doc 来生成可以直接导入的 postman collection 文件

##  快捷入口
*   [gateway-huawei](http://39.106.151.167:8080/index/download?name=gateway-huawei)

*   [fdc](http://39.106.151.167:8080/index/download?name=fdc)

*   [rbc](http://39.106.151.167:8080/index/download?name=rbc)

*   [老 openapi](http://39.106.151.167:8080/index/download?name=openapi)

*   [新 openapi](http://39.106.151.167:8080/index/download?name=openapiv2)

*   [businessapp](http://39.106.151.167:8080/index/download?name=businessapp)
    ```javascript
    {{host}}/observer/login
    {
        "account": "zhangchao",
        "password": "123456",
        "macAddress": "90:94:97:C2:F0:2E",
        "version": "4.5.13"
    }
    ```

*   [api-gateway-fdc](http://39.106.151.167:8080/index/download?name=api-gateway-fdc)

*   [api-gateway-rbc](http://39.106.151.167:8080/index/download?name=api-gateway-rbc)

*   ***[ahs-partner](http://39.106.151.167:8080/index/download?name=ahs-partner)***
    ```javascript
    // ahs-partner 的鉴权
    // 所有接口的 Authorization 头的值都是 postman 的环境变量 {{authorization}} 
    // 把如下代码贴到登陆接口的 Test 标签即可
    
    pm.test("set environment variable authorization", function () {
        var jwtToken = responseHeaders["Token"];
        pm.environment.set("authorization", "Bearer " + jwtToken);
    });
    ```
*   ***[dubai](http://39.106.151.167:8080/index/download?name=dubai)***
    ```javascript
    // 1.在 erp-gateway login 接口回调加这段脚本并调用,确保 ahs-guid 被打入环境变量
    // 2.调 ob-users/login 登陆ob用户
    // 3.调 ob-users/set-shop 选择门店
    pm.test("set environment variable ahs-guid",function(){
        var jsonData=pm.response.json();
        var ahsGuid = jsonData["data"];
        pm.environment.set("ahs-guid", ahsGuid);
    });
    ```

*   ***[duha](http://localhost:8081/index/download?name=duha)***
    ```bash
    // 1.在 erp-gateway login 接口回调加这段脚本把 erp-gateway 的token打入环境变量
    pm.test("set environment variable ahs-guid",function(){
        var jsonData=pm.response.json();
        var ahsGuid = jsonData["data"];
        pm.environment.set("ahs-guid", ahsGuid);
    });

    // 2.修改 ob/sync-session 的参数如下
    {
      "cityId" : 1,
      "shopId" : 1,
      "accessToken" : "{{ahs-guid}}"
    }
    ```

### TODO
-   [x] MULTIPART_FORM_DATA的支持
-   [ ] 序列化器的选择
-   [ ] 可以由一个具体的 swagger api 直接生成 feignClient 代码



