package com.fishshell.dk.service.model;

import lombok.Data;

import java.util.Map;

/**
 * @author alfred.zhou
 * @since 2019-01-08
 */
@Data
public class StpOptionSource {
    private Map<String,StpOptionModel> source;
}
