package com.fishshell.dk.service.util;

/**
 * @author alfred.zhou
 * @since 2018/12/5
 */
public class StpException extends RuntimeException {
    public StpException(String message) {
        super(message);
    }

    public static void throwNoImplementException() throws StpException {
        throw new StpException("执行了未实现的代码");
    }
}