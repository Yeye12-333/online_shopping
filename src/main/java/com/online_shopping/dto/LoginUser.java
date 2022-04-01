package com.online_shopping.dto;


import lombok.Data;
import lombok.ToString;

/**
 * @author ASUS
 */
@Data
@ToString
public class LoginUser {

    private String username;
    private String password;
    private Integer rememberMe;

}
