package com.fishshell.dk.service.model.swagger;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fishshell.dk.service.model.swagger.enumerate.EnumSwaggerApiParameterType;
import lombok.Data;

import java.util.Map;

/**
 * swagger 中的引用对象定义
 *
 * @author alfred.zhou
 * @since 2018/11/30
 */
@Data
public class SwaggerDefinition {
    private EnumSwaggerApiParameterType type;
    private Map<String, SwaggerDefinition> properties;
    private SwaggerDefinition items;
    @JsonAlias("$ref")
    private String ref;

    private String title;
    private String format;
    private String description;
    private String[] required;
//    private SwaggerDefinition additionalProperties;
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
