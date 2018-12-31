package com.fishshell.dk.service.model.postman;

import lombok.Data;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class PostmanItem {
    private String name;

    private PostmanItemRequest request;

//    private Object[] response;
}
