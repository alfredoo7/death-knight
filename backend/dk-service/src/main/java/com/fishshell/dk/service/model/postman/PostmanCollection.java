package com.fishshell.dk.service.model.postman;

import lombok.Data;

import java.util.List;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class PostmanCollection {
    private Object[] variables;
    private PostmanInfo info;
    private List<PostmanFolder> item;
}
