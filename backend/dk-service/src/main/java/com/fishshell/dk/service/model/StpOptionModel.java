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
     * 是否使用 {{host}} 环境变量代替写死 host/basePath
     */
    private Boolean useHostEnvVariables;

    /**
     * 添加的 headers 信息
     */
    private Map<String, String> headers;
}
