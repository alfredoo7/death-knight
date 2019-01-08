package com.fishshell.dk.service.model;


import com.fishshell.dk.service.model.swagger.SwaggerDoc;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 生成配置，初始化依赖于 {@link StpOptionModel} 和 {@link SwaggerDoc}
 *
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Data
public class StpGenerateModel {
    public StpGenerateModel(SwaggerDoc swaggerDoc, StpOptionModel stpOptionModel) {
        Date now = new Date();
        this.date = now;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        this.dateFormat = sdf.format(now);

        if (swaggerDoc.getInfo().getTitle() != null && swaggerDoc.getInfo().getTitle().length() > 0) {
            this.folderName = getHashName(swaggerDoc.getInfo().getTitle());
        } else {
            this.folderName = getHashName("unknowFolder");
        }

        this.schema = "https://schema.getpostman.com/json/collection/v2.0.0/collection.json";
        this.postmanId = "8286c9b3-15b4-0b9a-40ba-358689c38721";
        this.headers = stpOptionModel.getHeaders();

        if (stpOptionModel.getUseHostEnvVariables()) {
            this.host = "{{host}}";
        } else {
            this.host = swaggerDoc.getHost();
        }
        if (!StringUtils.isEmpty(stpOptionModel.getOverrideBasePath())) {
            this.basePath = stpOptionModel.getOverrideBasePath();
        } else {
            this.basePath = swaggerDoc.getBasePath();
        }
    }

    private Date date;
    /**
     * 时间戳
     */
    private String dateFormat;
    private String folderName;
    private String schema;
    private String postmanId;

    /**
     * 添加的 headers 信息
     */
    private Map<String, String> headers;

    private String host;
    private String basePath;

    private String getHashName(String name) {
        return name + "_" + this.dateFormat;
    }
}
