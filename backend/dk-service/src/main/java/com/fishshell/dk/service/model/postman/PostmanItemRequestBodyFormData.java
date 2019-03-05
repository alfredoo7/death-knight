package com.fishshell.dk.service.model.postman;

import lombok.Builder;
import lombok.Data;

/**
 * @author alfred.zhou
 * @since 2019-03-05
 */
@Data
@Builder
public class PostmanItemRequestBodyFormData {
    private String key;
    private String type;
    private String src;
    private String description;
}
