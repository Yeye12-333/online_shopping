package com.online_shopping.pojo;

import lombok.Data;

/**
 * @author yeye
 * @date 2022/4/1  23:26
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String sex;
    private String tel;
    private String email;
    private String hobby;
}
