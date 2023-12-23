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
import org.springframework.util.DigestUtils;

import java.util.List;

@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    protected UsersMapper usersMapper;

    @Override
    public CommonResult createUsers(Users users) {
        // 检验是否已存在该用户
        Users oldUsers = usersMapper.GetUsersByUserId(users.getUserId());
        // 返回错误信息
        if (oldUsers != null) {
            return CommonResult.fail("该用户已存在");
        }

        //对传入的 password 字段加密处理
        String md5Password = DigestUtils.md5DigestAsHex(users.getPassword().getBytes());
        users.setPassword(md5Password);
        boolean insertSuccess = SqlHelper.retBool(usersMapper.insert(users));
        if (!insertSuccess) {
            log.info("插入users表失败", users);
            return CommonResult.fail("插入users表失败");
        }
        return CommonResult.success(users);
    }

    @Override
    public CommonResult login(UsersLoginReq usersLoginReq) {
        //对传入的 password 字段加密处理
        String md5Password = DigestUtils.md5DigestAsHex(usersLoginReq.getPassword().getBytes());
        usersLoginReq.setPassword(md5Password);
        Users users = usersMapper.UserLogin(usersLoginReq);
        if (users != null) {
            return CommonResult.success(users);
        } else return CommonResult.fail("查询users表失败");
    }

    @Override
    public CommonResult getUsersById(String id) {
        Users users = usersMapper.selectById(id);
        if (users != null) {
            return CommonResult.success(users);
        } else return CommonResult.fail("查询users表失败");
    }

    @Override
    public CommonResult getUserByUserNameAndUserId(String name, String userId) {
        Users users = usersMapper.getUserByUserNameAndUserId(name, userId);
        if (users != null) {
            return CommonResult.success(users);
        } else return CommonResult.fail("查询users表失败");
    }

    @Override
    public CommonResult getUsersPage(UsersPageReq req) {
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1) * pageSize);
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
    public CommonResult updateUsers(Users users) {
        Users oldUsers = usersMapper.selectById(users.getId());
        // 检测是否设置了password字段更新
        if (users.getPassword() != null) {
            //对传入的 password 字段加密处理
            String md5Password = DigestUtils.md5DigestAsHex(users.getPassword().getBytes());
            users.setPassword(md5Password);
        } else {
            users.setPassword(oldUsers.getPassword());
        }
        Assert.notNull(oldUsers, "修改users表失败，表中查询不到对应usersId的教师");
        if (SqlHelper.retBool(baseMapper.updateById(users))) {
            return CommonResult.success(users);
        } else return CommonResult.fail("更新users表失败");
    }

    @Override
    public CommonResult deleteUsers(String id) {
        Users users = usersMapper.selectById(id);
        Assert.notNull(users, "删除users表数据失败，表中查询不到对应usersId的申请");
        if (SqlHelper.retBool(baseMapper.deleteById(id))) {
            return CommonResult.success(users);
        } else return CommonResult.fail("删除users表数据失败");
    }
}