package com.online_shopping.mapper;

import com.online_shopping.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yeye
 * @date 2022/4/1  23:33
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 根据用户密码查询
     * @param user 用户
     * @return 集合
     */
    User findBy(String username);

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return
     */
    @Select("select * from user where id=#{id} ;")
    User findById(int id);
}
