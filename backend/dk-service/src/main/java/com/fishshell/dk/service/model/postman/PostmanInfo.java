package com.fishshell.dk.service.model.postman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class PostmanInfo {
    @JsonProperty("name")
    private String folderName;

    @JsonProperty("_postman_id")
    private String postmanId;

    private String description;
    private String schema;
}
