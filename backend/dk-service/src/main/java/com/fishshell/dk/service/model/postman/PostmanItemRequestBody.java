package com.fishshell.dk.service.model.postman;

import lombok.Data;

import java.util.List;

/**
 * @author alfred.zhou
 * @since 2018/12/3
 */
@Data
public class PostmanItemRequestBody {
    private String mode;
    private String raw;
    private List<PostmanItemRequestBodyFormData> formdata;
}
