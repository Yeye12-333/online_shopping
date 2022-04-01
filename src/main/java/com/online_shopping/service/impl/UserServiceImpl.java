package com.online_shopping.service.impl;

import com.online_shopping.mapper.UserMapper;
import com.online_shopping.pojo.User;
import com.online_shopping.service.UserService;
import com.online_shopping.util.JwtTokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yeye
 * @date 2022/4/1  23:30
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public Map<String, Object> getUsers(User user) {
        System.out.println(user);
        Map<String,Object> message = new HashMap<>();
        User findUser = userMapper.findBy(user.getUsername());
        if(user != null){
            if(user.getPassword().equals(findUser.getPassword())){
                String token = JwtTokenUtils.createToken(user.getUsername(), user.getId(), false);
                message.put("message", "登录成功！");
                message.put("token", token);
            }else {
                message.put("message", "密码错误！");
            }
        }else {
            message.put("message","用户不存在");
        }
        return message;
    }

    @Override
    public User getUserInfo(int id) {
        return userMapper.findById(id);
    }


}
