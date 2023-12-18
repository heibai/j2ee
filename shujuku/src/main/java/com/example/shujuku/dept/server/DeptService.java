package com.example.shujuku.dept.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.dept.bean.Dept;
import com.example.shujuku.req.DeptPageReq;


public interface DeptService extends IService<Dept> {

    public CommonResult createDept(Dept dept);

    public CommonResult getDeptByDeptId(String deptId);

    public CommonResult getDeptPage(DeptPageReq deptPageReq);

    public CommonResult getDeptList(DeptPageReq req);

    public CommonResult updateDept(Dept dept);

    public CommonResult deleteDept(String deptId);
}
