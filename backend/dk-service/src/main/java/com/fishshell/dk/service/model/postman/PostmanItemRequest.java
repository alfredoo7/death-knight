package com.fishshell.dk.service.model.postman;

import lombok.Data;

import java.util.List;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class PostmanItemRequest {
    // TODO:(by zf):枚举
    private String method;

    private String url;
//    private PostmanItemRequestUrl url;

    private List<PostmanItemRequestHeader> header;

    private PostmanItemRequestBody body;

    private String description;
}
