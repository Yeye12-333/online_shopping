package com.online_shopping.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "测试Swagger")
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return null;
    }
}
