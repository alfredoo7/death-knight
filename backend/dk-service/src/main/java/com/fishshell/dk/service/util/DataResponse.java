package com.fishshell.dk.service.util;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResponse<T> {
    private Integer code;
    private T data;
}
