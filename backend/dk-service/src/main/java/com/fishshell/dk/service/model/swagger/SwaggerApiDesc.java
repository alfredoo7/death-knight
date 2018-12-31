package com.fishshell.dk.service.model.swagger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fishshell.dk.service.model.swagger.enumerate.EnumContentType;
import lombok.Data;

import java.util.List;

/**
 * swagger-api 的描述
 *
 * @author alfred.zhou
 * @since 2018/11/29
 */
@Data
public class SwaggerApiDesc {
    private List<String> tags;

    private String summary;

    /**
     * "operationId": "pricePropertiesUsingGET_2"
     */
    private String operationId;

    private List<EnumContentType> produces;
    private Boolean deprecated;

    private List<SwaggerApiParameter> parameters;

    @JsonIgnore
    private Object responses;

    private List<EnumContentType> consumes;

    private String description;
}
