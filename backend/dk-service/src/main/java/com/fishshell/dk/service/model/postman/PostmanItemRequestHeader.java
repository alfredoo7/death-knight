package com.fishshell.dk.service.model.postman;

import lombok.Data;

/**
 * @author alfred.zhou
 * @since 2018/12/2
 */
@Data
public class PostmanItemRequestHeader {
    private String key;
    private String value;//"application/json",
    private String description;
}
