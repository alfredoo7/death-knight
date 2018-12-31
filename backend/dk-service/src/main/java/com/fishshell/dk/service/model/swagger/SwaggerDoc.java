package com.fishshell.dk.service.model.swagger;

import com.fishshell.dk.service.model.swagger.enumerate.EnumHttpMethod;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * swagger 文档
 *
 * @author alfred.zhou
 * @since 2018/11/29
 */
@Data
public class SwaggerDoc {
    /**
     * "swagger": "2.0"
     */
    private String swagger;
    private String host;
    private SwaggerInfo info;
    /**
     * "basePath": "/foundation-data-center"
     */
    private String basePath;
    private List<SwaggerTag> tags;
    private Map<String, Map<EnumHttpMethod, SwaggerApiDesc>> paths;
    //private Map<String, SwaggerDefinition> definitions;
    private Map<String, SwaggerDefinition> definitions;
}