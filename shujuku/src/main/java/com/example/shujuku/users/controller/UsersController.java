package com.example.shujuku.users.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.UsersLoginReq;
import com.example.shujuku.req.UsersPageReq;
import com.example.shujuku.users.bean.Users;
import com.example.shujuku.users.server.UsersService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("用户相关接口")
@Slf4j
@RestController
@RequestMapping("users")
@Validated
public class UsersController {

    @Autowired
    private UsersService usersService;

    @ApiModelProperty("创建用户用户")
    @PostMapping("/createUser")
    public CommonResult createUser(@RequestBody Users users){
        return usersService.createUsers(users);
    }

    @ApiModelProperty("更新用户")
    @PostMapping("/login")
    public CommonResult login(@RequestBody UsersLoginReq usersLoginReq){
        return usersService.login(usersLoginReq);
    }


    @ApiModelProperty("查询用户")
    @GetMapping("/getUser")
    public CommonResult getUser(@Param("userId") String userId){
        return usersService.getUsersByUserId(userId);
    }

    @ApiModelProperty("分页查询用户")
    @GetMapping("/getUsersPage")
    public CommonResult getUsersPage(UsersPageReq usersPageReq){
        return usersService.getUsersList(usersPageReq);
    }

    @ApiModelProperty("更新用户")
    @PostMapping("/updateUser")
    public CommonResult updateUser(@RequestBody Users users){
        return usersService.updateUsers(users);
    }

    @ApiModelProperty("删除用户")
    @GetMapping("/deleteUser")
    public CommonResult deleteUser(@Param("usersId") String usersId){
        return usersService.deleteUsers(usersId);
    }
}
