package com.fishshell.dk.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishshell.dk.service.model.StpOptionModel;
import com.fishshell.dk.service.model.StpOptionSource;
import com.fishshell.dk.service.model.postman.PostmanCollection;
import com.fishshell.dk.service.model.swagger.SwaggerDoc;
import com.fishshell.dk.service.service.StpService;
import com.fishshell.dk.service.util.PageListResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    private final StpOptionSource stpOptionSource;

    @GetMapping(value = "")
    public PostmanCollection index() throws IOException {
        return index("ahs-partner", true, null);
    }

    @GetMapping(value = "download")
    public ResponseEntity download(@RequestParam("name") String name,
                                   @RequestParam(value = "useHost", defaultValue = "true") Boolean useHostEnvVariables,
                                   @RequestParam(value = "overrideBasePath", defaultValue = "") String overrideBasePath) throws IOException {
        PostmanCollection postmanCollection = index(name, useHostEnvVariables, overrideBasePath);

        byte[] body = mapper.writeValueAsBytes(postmanCollection);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + postmanCollection.getInfo().getFolderName() + ".json");
        ResponseEntity respEntity = new ResponseEntity(body, responseHeaders, HttpStatus.OK);
        return respEntity;
    }

    /**
     * 额外参数会大于默认参数
     *
     * @param name
     * @param useHostEnvVariables
     * @param overrideBasePath
     * @return
     * @throws IOException
     */
    private PostmanCollection index(String name, Boolean useHostEnvVariables, String overrideBasePath) throws IOException {
        name = name.toLowerCase();

        if (!stpOptionSource.getSource().containsKey(name)) {
            return null;
        }
        StpOptionModel stpOptionModel = stpOptionSource.getSource().get(name);
        if (useHostEnvVariables != null) {
            stpOptionModel.setUseHostEnvVariables(useHostEnvVariables);
        }
        if (!StringUtils.isEmpty(overrideBasePath) && overrideBasePath.startsWith("/")) {
            stpOptionModel.setOverrideBasePath(overrideBasePath);
        }

        URL url = new URL(stpOptionModel.getUrl());
        SwaggerDoc swaggerResource = mapper.readValue(url, SwaggerDoc.class);
        return stpService.convert(swaggerResource, stpOptionModel);
    }

    @GetMapping(value = "options")
    public PageListResponse<List<StpOptionModel>> options() {
        List<StpOptionModel> stpOptionModels = new ArrayList<>(stpOptionSource.getSource().values());
        return new PageListResponse<>(0, new ArrayList<>(stpOptionSource.getSource().values()), 0, stpOptionModels.size(), stpOptionModels.size());
    }
}