package com.online_shopping.service;

import com.online_shopping.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author yeye
 * @date 2022/4/1  23:29
 */
public interface UserService {

    /**
     * 登录
     * @param user 用户
     * @return 集合
     */
    Map<String, Object> getUsers(User user);


    /**
     * 根据id查询用户信息
     * @param id id
     * @return
     */
    User getUserInfo(int id);

}
