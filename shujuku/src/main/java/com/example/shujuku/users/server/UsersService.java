package com.example.shujuku.users.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.UsersLoginReq;
import com.example.shujuku.req.UsersPageReq;
import com.example.shujuku.users.bean.Users;

public interface UsersService extends IService<Users> {

    public CommonResult createUsers(Users users);

    public CommonResult getUsersById(String id);

    public CommonResult login(UsersLoginReq usersLoginReq);

    public CommonResult getUsersList(UsersPageReq req);

    public CommonResult updateUsers(Users users);

    public CommonResult deleteUsers(String id);

}
