package com.fishshell.dk.service.model.swagger.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author alfred.zhou
 * @since 2018/11/30
 */
public enum EnumSwaggerApiParameterType {
    INTEGER("integer"),
    NUMBER("number"),
    BOOLEAN("boolean"),
    STRING("string"),
    ARRAY("array"),
    OBJECT("object"),
    REF("ref"),
    FILE("file");

    private String value;

    @JsonCreator
    public static EnumSwaggerApiParameterType getItem(String value) throws Exception {
        for (EnumSwaggerApiParameterType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new Exception(String.format("type 的值无法解析 %s", value));
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    EnumSwaggerApiParameterType(String value) {
        this.value = value;
    }
}
