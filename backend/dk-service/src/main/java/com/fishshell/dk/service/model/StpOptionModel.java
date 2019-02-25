package com.fishshell.dk.service.model;

import lombok.Data;

import java.util.Map;

/**
 * 一些使用者选择的生成选项
 *
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class StpOptionModel {
    /**
     * 是否使用 {{host}} 环境变量代替写死 host
     */
    private Boolean useHostEnvVariables;

    /**
     * 是否重写 basePath
     */
    private String overrideBasePath;

    /**
     * 添加的 headers 信息
     */
    private Map<String, String> headers;

    private String url;

    private String key;
}
