package com.online_shopping.exception;

/**
 * @author yeye
 * @date 2022/3/30  22:59
 */
public class PermissionException extends Throwable {
    private String message;

    public PermissionException(String message){
        this.message = message;
    }
}
