package com.fishshell.dk.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishshell.dk.service.model.StpOptionModel;
import com.fishshell.dk.service.model.postman.PostmanCollection;
import com.fishshell.dk.service.model.swagger.SwaggerDoc;
import com.fishshell.dk.service.service.StpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * @author alfred.zhou
 * @since 2018/11/29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/index")
public class IndexController {
    private final StpService stpService;
    private final ObjectMapper mapper;

    @GetMapping(value = "")
    public PostmanCollection index() throws IOException {
        return index("openapi", "");
    }

    @GetMapping(value = "download")
    public ResponseEntity download(@RequestParam("name") @NotNull String name) throws IOException {
        name = name.toLowerCase();
        if (!"fdc".equals(name) && !"openapi".equals(name) && !"rbc".equals(name) && !"openapiv2".equals(name)) {
            return null;
        }
        PostmanCollection postmanCollection = index(name, "");
        byte[] body = mapper.writeValueAsBytes(postmanCollection);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + postmanCollection.getInfo().getFolderName() + ".collection");
        ResponseEntity respEntity = new ResponseEntity(body, responseHeaders, HttpStatus.OK);
        return respEntity;
    }

    private PostmanCollection index(String prefix, String json) throws IOException {
        StpOptionModel stpOptionModel = new StpOptionModel();
        stpOptionModel.setUseHostEnvVariables(true);
        if (("fdc").equals(prefix)) {
            json = "http://47.96.53.33:8080/foundation-data-center/v2/api-docs";
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Auth-User-Base-Info", "{\"userKey\": \"1805142000053113\", \"mobile\": \"13601858246\"}");
            headers.put("ahs-backend-agent", "{\"agentId\": 1, \"agencyId\": 0}");
            stpOptionModel.setHeaders(headers);
        }
        if ("openapi".equals(prefix)) {
            json = "http://uat-open.aihuishou.com/api/cp/swagger/v1/swagger.json";
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "{{authorization}}");
            stpOptionModel.setHeaders(headers);
        }
        if ("rbc".equals(prefix)) {
            json = "http://47.96.53.33:8080/recycle-business-center/v2/api-docs";
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Auth-User-Base-Info", "{\"userKey\": \"1805142000053113\", \"mobile\": \"13601858246\"}");
            headers.put("ahs-backend-agent", "{\"agentId\": 1, \"agencyId\": 0}");
            stpOptionModel.setHeaders(headers);
        }
        if ("openapiv2".equals(prefix)) {
            json = "http://uat-cooperator-openapi.aihuishou.com/cooperator-openapi/v2/api-docs";
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "{{authorization}}");
            stpOptionModel.setHeaders(headers);
        }

        URL url = new URL(json);
        SwaggerDoc swaggerResource = mapper.readValue(url, SwaggerDoc.class);
        return stpService.convert(swaggerResource, stpOptionModel);
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView mv) {
        mv.addObject("name", "jack");
        mv.setViewName("freemarker");
        return mv;
    }
}