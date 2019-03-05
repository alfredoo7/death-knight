package com.fishshell.dk.service.model.postman;

import lombok.Builder;
import lombok.Data;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
@Builder
public class PostmanItemRequestUrlQuery {
    private String key;
    private String value;
//    private Boolean equals;
    private String description;
}
