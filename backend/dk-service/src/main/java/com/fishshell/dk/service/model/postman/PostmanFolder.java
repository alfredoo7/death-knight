package com.fishshell.dk.service.model.postman;

import lombok.Data;

import java.util.List;

/**
 * 文件夹
 *
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class PostmanFolder {
    private String name;
    private String description;
    private List<PostmanItem> item;
}
