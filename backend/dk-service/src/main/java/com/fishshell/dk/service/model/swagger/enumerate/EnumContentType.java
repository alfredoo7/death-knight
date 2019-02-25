package com.fishshell.dk.service.model.swagger.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
public enum EnumContentType {
    WILDCARD("*/*"),
    APPLICATION_JSON("application/json"),
    TEXT_JSON("text/json"),
    APPLICATION_JSON_PATCH_JSON("application/json-patch+json"),
    TEXT_PLAIN("text/plain"),
    MULTIPART_FORM_DATA("multipart/form-data");

    private String value;

    @JsonCreator
    public static EnumContentType getItem(String value) throws Exception {
        for (EnumContentType contentType : values()) {
            if (contentType.getValue().equals(value)) {
                return contentType;
            }
        }
        throw new Exception("contentType 的值无法解析");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    EnumContentType(String value) {
        this.value = value;
    }
}
