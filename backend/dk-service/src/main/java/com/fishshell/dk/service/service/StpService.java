package com.fishshell.dk.service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishshell.dk.service.model.StpGenerateModel;
import com.fishshell.dk.service.model.StpOptionModel;
import com.fishshell.dk.service.model.postman.*;
import com.fishshell.dk.service.model.swagger.*;
import com.fishshell.dk.service.model.swagger.enumerate.EnumContentType;
import com.fishshell.dk.service.model.swagger.enumerate.EnumHttpMethod;
import com.fishshell.dk.service.model.swagger.enumerate.EnumSwaggerApiParameterIn;
import com.fishshell.dk.service.model.swagger.enumerate.EnumSwaggerApiParameterType;
import com.fishshell.dk.service.util.StpException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Swagger to Postman Service
 *
 * @author alfred.zhou
 * @since 2018/12/1
 */
@Service
@AllArgsConstructor
public class StpService {
    private final ObjectMapper mapper;

    public PostmanCollection convert(SwaggerDoc swaggerDoc, StpOptionModel stpOptionModel) {
        PostmanCollection postmanCollection = new PostmanCollection();
        StpGenerateModel stpGenerateModel = new StpGenerateModel(swaggerDoc, stpOptionModel);

        bindInfo(swaggerDoc, postmanCollection, stpGenerateModel);

        // 创建对应的子文件夹
        bindFolder(swaggerDoc, postmanCollection);

        bindItems(swaggerDoc, postmanCollection, stpGenerateModel);

        return postmanCollection;
    }

    private void bindInfo(SwaggerDoc swaggerDoc, PostmanCollection postmanCollection, StpGenerateModel stpGenerateModel) {
        PostmanInfo postmanInfo = new PostmanInfo();
        if (swaggerDoc.getInfo().getTitle() != null) {
            postmanInfo.setFolderName(stpGenerateModel.getFolderName());
        } else {
            postmanInfo.setFolderName(stpGenerateModel.getFolderName());
        }

        if (swaggerDoc.getInfo().getDescription() != null) {
            postmanInfo.setDescription(swaggerDoc.getInfo().getDescription());
        }

        postmanInfo.setPostmanId(stpGenerateModel.getPostmanId());
        postmanInfo.setSchema(stpGenerateModel.getSchema());
        postmanCollection.setInfo(postmanInfo);
    }

    /**
     * V2 版本可以根据 tag 信息生成 folder
     */
    private void bindFolder(SwaggerDoc swaggerDoc, PostmanCollection postmanCollection) {
        // 将 swagger 的 tag 作为 postman 建立子文件夹的依据
        List<PostmanFolder> postmanFolders = new ArrayList<>();

        if (CollectionUtils.isEmpty(swaggerDoc.getTags())) {
            return;
        }
        for (SwaggerTag tag : swaggerDoc.getTags()) {
            PostmanFolder postmanFolder = new PostmanFolder();
            postmanFolder.setName(tag.getName());
            postmanFolder.setDescription(tag.getDescription());
            postmanFolders.add(postmanFolder);
        }
        postmanCollection.setItem(postmanFolders);
    }

    /**
     * V1 版本需要根据每个 api 的 tags 描述来创建 folder
     */
    private void bindFolder(List<String> tags, PostmanItem pi, PostmanCollection postmanCollection) {
        if (postmanCollection.getItem() == null) {
            postmanCollection.setItem(new ArrayList<>());
        }
        for (String tag : tags) {
            Optional<PostmanFolder> first = postmanCollection.getItem().stream().filter(folder -> folder.getName().equals(tag)).findFirst();
            PostmanFolder folder;
            // 如果不存在创建文件夹
            if (!first.isPresent()) {
                folder = new PostmanFolder();
                folder.setName(tag);
                postmanCollection.getItem().add(folder);
            } else {
                folder = first.get();
            }

            if (folder.getItem() == null) {
                folder.setItem(new ArrayList<>());
            }
            folder.getItem().add(pi);
        }
    }

    private void bindItems(SwaggerDoc swaggerDoc, PostmanCollection postmanCollection, StpGenerateModel stpGenerateModel) {
        // 开始遍历 paths 节点
        for (Map.Entry<String, Map<EnumHttpMethod, SwaggerApiDesc>> pathEntry : swaggerDoc.getPaths().entrySet()) {
            bindItem(pathEntry, postmanCollection, stpGenerateModel, swaggerDoc.getDefinitions());
        }
    }

    /**
     * 核心方法,将一个 {@link SwaggerDoc} paths 节点下的一个 Map.Entry bind 为 {@link PostmanItem} 列表,并依附到 {@link PostmanCollection} 对应 item(tag) 下
     */
    private void bindItem(
        Map.Entry<String, Map<EnumHttpMethod, SwaggerApiDesc>> pathEntry,
        PostmanCollection postmanCollection,
        StpGenerateModel stpGenerateModel,
        Map<String, SwaggerDefinition> definitions) {

        // 多个 method 不同的 api 可能使用相同 url ，所以 url 定义在循环外复用
        String url = stpGenerateModel.getHost() + (StringUtils.isEmpty(stpGenerateModel.getBasePath()) ? "" : stpGenerateModel.getBasePath()) + pathEntry.getKey();

//        test
//        if ("{{host}}/erp/property-tags".equals(url)) {
//            int i = 1;
//            int i1 = i;
//        }

        for (Map.Entry<EnumHttpMethod, SwaggerApiDesc> methodDescEntry : pathEntry.getValue().entrySet()) {
            SwaggerApiDesc desc = methodDescEntry.getValue();
            PostmanItem pi = new PostmanItem();
            pi.setName(desc.getSummary());
            PostmanItemRequest pir = new PostmanItemRequest();
            pi.setRequest(pir);
            pir.setMethod(methodDescEntry.getKey().getValue().toUpperCase());
            boolean isUnknown = false;

            if (desc.getDescription() != null) {
                pir.setDescription(desc.getDescription());
            }

            // headers
            List<PostmanItemRequestHeader> headers = new ArrayList<>();
            if (desc.getConsumes() != null) {
                for (EnumContentType contentType : desc.getConsumes()) {
                    if (contentType == EnumContentType.APPLICATION_JSON) {
                        PostmanItemRequestHeader contentTypeHeader = new PostmanItemRequestHeader();
                        contentTypeHeader.setKey("Content-Type");
                        contentTypeHeader.setValue(contentType.getValue());
                        headers.add(contentTypeHeader);
                    } else if (contentType == EnumContentType.MULTIPART_FORM_DATA) {
                        isUnknown = true;
                        break;
                    }
                }
            }
            if (isUnknown) {
                continue;
            }

            if (!CollectionUtils.isEmpty(stpGenerateModel.getHeaders())) {
                for (Map.Entry<String, String> customerHeaderEntry : stpGenerateModel.getHeaders().entrySet()) {
                    PostmanItemRequestHeader contentTypeHeader = new PostmanItemRequestHeader();
                    contentTypeHeader.setKey(customerHeaderEntry.getKey());
                    contentTypeHeader.setValue(customerHeaderEntry.getValue());
                    headers.add(contentTypeHeader);
                }
            }
            if (!CollectionUtils.isEmpty(headers)) {
                pir.setHeader(headers);
            }
            //headers end

            // url 副本
            StringBuilder urlCopy = new StringBuilder(new String(url));
            if (desc.getParameters() != null) {
                for (SwaggerApiParameter parameter : desc.getParameters()) {


                    Object defaultValue = getJavaTypeDefaultFromParameter(parameter, definitions);
                    if (parameter.getIn() == EnumSwaggerApiParameterIn.PATH) {
                        // PATH -> 使用一个默认值替换占位符
                        String format = String.format("{%s}", parameter.getName());
                        urlCopy = new StringBuilder(urlCopy.toString().replace(format, defaultValue.toString()));
                    }
                    if (parameter.getIn() == EnumSwaggerApiParameterIn.BODY) {
                        // BODY -> 生成 raw 风格的 body
                        if (defaultValue != null) {
                            try {
                                String raw = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(defaultValue);
                                PostmanItemRequestBody body = new PostmanItemRequestBody();
                                body.setMode("raw");
                                body.setRaw(raw);
                                pir.setBody(body);
                            } catch (IOException e) {

                            }
                        }
                    }
                    if (parameter.getIn() == EnumSwaggerApiParameterIn.QUERY) {
                        // QUERY -> url 拼接
                        if (urlCopy.toString().contains("?")) {
                            urlCopy.append("&").append(parameter.getName()).append("=").append(defaultValue);
                        } else {
                            urlCopy.append("?").append(parameter.getName()).append("=").append(defaultValue);
                        }
                    } else {

                    }
                }
            }
            pir.setUrl(urlCopy.toString());
            bindFolder(desc.getTags(), pi, postmanCollection);
        }
    }

    /**
     * 得到 java 类型的默认值
     */
    private Object getJavaTypeDefaultFromParameter(SwaggerApiParameter parameter, Map<String, SwaggerDefinition> definitions) {
        if (parameter.getSchema() != null) {
            return getJavaTypeDefaultFromParameter(parameter.getSchema(), definitions);
        }

        if (parameter.getRef() != null) {
            String replace = parameter.getRef().replace("#/definitions/", "");
            SwaggerDefinition definition = definitions.get(replace);
            return getJavaTypeDefaultFromDefinition(definition, definitions);
        }

        if (parameter.getType() == null) {
            return "unknown";
        }

        switch (parameter.getType()) {
            case INTEGER:
                return getValueTypeDefault(parameter.getType(), parameter.getFormat());
            case NUMBER:
                return getValueTypeDefault(parameter.getType(), parameter.getFormat());
            case BOOLEAN:
                return getValueTypeDefault(parameter.getType(), parameter.getFormat());
            case STRING:
                return getValueTypeDefault(parameter.getType(), parameter.getFormat());

            case ARRAY:
                // 如果 type = array ,那么分析 items 字段
                if (parameter.getItems() != null) {
                    Object o = getJavaTypeDefaultFromParameter(parameter.getItems(), definitions);
                    List<Object> array = new ArrayList<>();
                    array.add(o);
                    return array;
                } else {
                    // 兼容一种特殊的情况
                    return Arrays.asList("unknown");
                }

            case OBJECT:
                return "unknown";

            default:
                return "unknown";
        }
    }

    /**
     * 得到 java 类型的默认值
     */
    private Object getJavaTypeDefaultFromDefinition(SwaggerDefinition definition, Map<String, SwaggerDefinition> definitions) {
        if (definition.getRef() != null) {
            String replace = definition.getRef().replace("#/definitions/", "");
            SwaggerDefinition refDefinition = definitions.get(replace);
            return getJavaTypeDefaultFromDefinition(refDefinition, definitions);
        }

        switch (definition.getType()) {
            case INTEGER:
                return getValueTypeDefault(definition.getType(), definition.getFormat());
            case NUMBER:
                return getValueTypeDefault(definition.getType(), definition.getFormat());
            case BOOLEAN:
                return getValueTypeDefault(definition.getType(), definition.getFormat());
            case STRING:
                return getValueTypeDefault(definition.getType(), definition.getFormat());

            case ARRAY:
                return getRefTypeDefault(definition, definitions);
            case OBJECT:
                return getRefTypeDefault(definition, definitions);
            default:
                //StpException.throwNoImplementException();
                return "unknown";
        }
    }

    /**
     * 得到 java 引用类型的默认值
     */
    private Object getRefTypeDefault(SwaggerDefinition definition, Map<String, SwaggerDefinition> definitions) {
        HashMap<String, Object> result = new HashMap<>();
        Map<String, SwaggerDefinition> properties = definition.getProperties();

        if (CollectionUtils.isEmpty(properties)) {
            return "unknown";
        }

        for (Map.Entry<String, SwaggerDefinition> entry : properties.entrySet()) {
            SwaggerDefinition sd = entry.getValue();
            if (sd.getRef() != null) {
                String replace = sd.getRef().replace("#/definitions/", "");
                SwaggerDefinition refDefinition = definitions.get(replace);
                Object value = getRefTypeDefault(refDefinition, definitions);
                result.put(entry.getKey(), value);
            } else if (sd.getType() == EnumSwaggerApiParameterType.OBJECT) {
                Object refEntry = getJavaTypeDefaultFromDefinition(sd, definitions);
                result.put(entry.getKey(), refEntry);
            } else if (sd.getType() == EnumSwaggerApiParameterType.ARRAY) {
                List<Object> items = new ArrayList<>();
                Object item = getJavaTypeDefaultFromDefinition(sd.getItems(), definitions);
                items.add(item);
                result.put(entry.getKey(), items);
            } else {
                Object value = getValueTypeDefault(sd.getType(), sd.getFormat());
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }

    /**
     * 得到 java 值类型的默认值,如果无法推断引发 {@link StpException}
     */
    private Object getValueTypeDefault(EnumSwaggerApiParameterType type, String format) {
        if (type == null) {
            return "unknown";
        }
        switch (type) {
            case INTEGER:
                return 1;

            case NUMBER:
                return 1;
            case BOOLEAN:
                return true;
            case STRING:
                return "string1";

            case ARRAY:
                return "unknown";
            default:
                return "unknown";
        }
    }
}