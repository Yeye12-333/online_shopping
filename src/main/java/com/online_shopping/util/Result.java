package com.online_shopping.util;

/**
 * @author yeye
 * @date 2022/1/20  15:19
 */

import lombok.Data;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一数据返回格式
 */
@Data
public class Result {

    private Integer code;
    private String message;
    private Boolean success;
    private Object data = new HashMap<String, Object>();

    public Result() {

    }

    public static Result ok(){
        Result result = new Result();
        result.setCode(20000);
        result.setMessage("成功");
        result.setSuccess(true);
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setCode(20001);
        result.setMessage("失败");
        result.setSuccess(false);
        return result;
    }


    public static Result auto(boolean auto) {
        return auto ? ok() : error();
    }

    public Result code(Integer code){
        setCode(code);
        return this;
    }

    public Result message(String message){
        setMessage(message);
        return this;
    }

    @SneakyThrows
    public Result data(String key, Object value){
        if (data instanceof Map){
            Map<String, Object> map = (Map<String, Object>) data;
            map.put(key, value);
        }else {
            throw new Exception("Result.data数据类型错误");
        }
        return this;
    }

    public Result data(Object value){
        data = value;
        return this;
    }

    public Result list(List<?> list){
        data("rows", list);
        return this;
    }

}
