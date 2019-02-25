package com.fishshell.dk.service.model.swagger.enumerate;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author alfred.zhou
 * @since 2018/11/30
 */
public enum EnumSwaggerApiParameterIn {
    PATH("path"),
    BODY("body"),
    QUERY("query"),
    FORM_DATA("formData");

    private String value;

    @JsonCreator
    public static EnumSwaggerApiParameterIn getItem(String value) throws Exception {
        for (EnumSwaggerApiParameterIn in : values()) {
            if (in.getValue().equals(value)) {
                return in;
            }
        }
        throw new Exception("in 的值无法解析");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    EnumSwaggerApiParameterIn(String value) {
        this.value = value;
    }
}
