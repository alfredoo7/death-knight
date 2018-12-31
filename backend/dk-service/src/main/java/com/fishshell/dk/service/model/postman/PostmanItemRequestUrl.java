package com.fishshell.dk.service.model.postman;

import lombok.Data;

import java.util.List;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class PostmanItemRequestUrl {
    private String raw;
    private List<String> host;
    private List<String> path;
    private List<PostmanItemRequestUrlQuery> query;
//    private Object[] variable;
}
