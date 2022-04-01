package com.online_shopping.service.impl;

import com.online_shopping.dto.JwtUser;
import com.online_shopping.mapper.UserMapper;
import com.online_shopping.pojo.User;
import com.online_shopping.service.CustomUserService;
import com.online_shopping.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yeye
 * @date 2022/3/21  20:46
 */
@Service
@Slf4j
public class CustomUserServiceImpl implements CustomUserService {

    @Resource
    private UserMapper userInfoService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user= userInfoService.findBy(s);

        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        System.out.println("userDetail=" + user);
        return new JwtUser(user);
    }
}
