package com.fishshell.dk.service.model.swagger.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author alfred.zhou
 * @since 2018/12/1
 */
public enum EnumHttpMethod {
    GET("get"),
    POST("post"),
    PUT("put"),
    HEAD("head");

    private String value;

    @JsonCreator
    public static EnumHttpMethod getItem(String value) throws Exception {
        for (EnumHttpMethod method : values()) {
            if (method.getValue().equals(value)) {
                return method;
            }
        }
        throw new Exception("method 的值无法解析");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    EnumHttpMethod(String value) {
        this.value = value;
    }
}
