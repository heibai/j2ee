package com.example.shujuku.users.server.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.req.UsersLoginReq;
import com.example.shujuku.req.UsersPageReq;
import com.example.shujuku.users.bean.Users;
import com.example.shujuku.mapper.UsersMapper;
import com.example.shujuku.users.server.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    protected UsersMapper usersMapper;

    @Override
    public CommonResult createUsers(Users users){
        boolean insertSuccess = SqlHelper.retBool(usersMapper.insert(users));
        if(!insertSuccess){
            log.info("插入users表失败",users);
            return CommonResult.fail("插入users表失败");
        }
        return CommonResult.success(users);
    }

    @Override
    public CommonResult login(UsersLoginReq usersLoginReq){
        Users users = usersMapper.UserLogin(usersLoginReq);
        if(users != null){
            return CommonResult.success(users);
        }else return CommonResult.fail("查询users表失败");
    }

    @Override
    public CommonResult getUsersById(String id){
        Users users = usersMapper.selectById(id);
        if(users != null){
            return CommonResult.success(users);
        }else return CommonResult.fail("查询users表失败");
    }

    @Override
    public CommonResult getUsersList(UsersPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Users> usersList = usersMapper.getUsersList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Users> page = new Page<>(pageNo, pageSize);
        page.setRecords(usersList);
        page.setTotal(usersList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateUsers(Users users){
        Users oldUsers = usersMapper.selectById(users.getUserId());
        Assert.notNull(oldUsers, "修改users表失败，表中查询不到对应usersId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(users))){
            return CommonResult.success(users);
        }else return CommonResult.fail("更新users表失败");
    }

    @Override
    public CommonResult deleteUsers(String id){
        Users users = usersMapper.selectById(id);
        Assert.notNull(users, "删除users表数据失败，表中查询不到对应usersId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(id))){
            return CommonResult.success(users);
        }else return CommonResult.fail("删除users表数据失败");
    }
}