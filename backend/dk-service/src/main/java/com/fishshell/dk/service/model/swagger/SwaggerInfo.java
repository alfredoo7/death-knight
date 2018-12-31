package com.fishshell.dk.service.model.swagger;

import lombok.Data;

/**
 * @author alfred.zhou
 * @since 2018/11/29
 */
@Data
public class SwaggerInfo {
    private String description;
    private String version;
    private String title;

//    @JsonIgnore
//    private String termsOfService;
}
