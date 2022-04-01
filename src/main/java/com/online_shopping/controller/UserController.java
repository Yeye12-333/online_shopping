package com.online_shopping.controller;

import com.online_shopping.pojo.User;
import com.online_shopping.service.UserService;
import com.online_shopping.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yeye
 * @date 2022/4/2  0:10
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody User user){
        System.out.println(user);
        return Result.ok().data(userService.getUsers(user));
    }

    @GetMapping("/userInfo")
    public Result getUserInfo(int loginUserId){
        System.out.println(loginUserId);
        return Result.ok().data(userService.getUserInfo(loginUserId));
    }

}
