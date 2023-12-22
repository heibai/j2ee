package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.UsersLoginReq;
import com.example.shujuku.req.UsersPageReq;
import com.example.shujuku.users.bean.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UsersMapper extends BaseMapper<Users>{

    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    Users GetUsersByUserId(String userId);

    @Select("SELECT * FROM users WHERE id = #{id}")
    Users GetUsersById(String id);

    Users UserLogin(UsersLoginReq usersLoginReq);

    List<Users> getUsersList(UsersPageReq req);

}