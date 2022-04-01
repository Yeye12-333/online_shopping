package com.online_shopping.exception;

/**
 * @author yeye
 * @date 2022/3/30  23:19
 */
public class SugoException extends Exception{

    private String message;

    public SugoException(String message){
        this.message = message;
    }
}
