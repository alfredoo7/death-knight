package com.fishshell.dk.service.model.swagger;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fishshell.dk.service.model.swagger.enumerate.EnumSwaggerApiParameterIn;
import com.fishshell.dk.service.model.swagger.enumerate.EnumSwaggerApiParameterType;
import lombok.Data;

/**
 * swagger-api 的参数描述
 *
 * @author alfred.zhou
 * @since 2018/11/29
 */
@Data
public class SwaggerApiParameter {
    private String name;
    private EnumSwaggerApiParameterType type;
    private EnumSwaggerApiParameterIn in;
    private SwaggerApiParameter items;
    private SwaggerApiParameter schema;
    @JsonAlias("$ref")
    private String ref;

    private Boolean required;
    private String description;
    private String format;
    private String collectionFormat;

//    @JsonAlias("enum")
//    private List<Object> enumm;
//    @JsonIgnore
//    private Integer maximum;
//    @JsonIgnore
//    private Integer minimum;
//    @JsonIgnore
//    private Integer maxLength;
//    @JsonIgnore
//    private Integer minLength;
//    @JsonIgnore
//    private Boolean readOnly;
}
