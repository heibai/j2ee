package com.example.shujuku.dept.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.dept.bean.Dept;
import com.example.shujuku.dept.server.DeptService;
import com.example.shujuku.req.DeptPageReq;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("系相关接口")
@Slf4j
@RestController
@RequestMapping("dept")
@Validated
public class DeptController {
    @Autowired
    private DeptService deptService;

    @ApiModelProperty("创建系")
    @PostMapping("/createDept")
    public CommonResult createDept(@RequestBody Dept dept){
        return deptService.createDept(dept);
    }

    @ApiModelProperty("查询系")
    @GetMapping("/getDept")
    public CommonResult getDept(@Param("deptId") String deptId){
        return deptService.getDeptByDeptId(deptId);
    }

    @ApiModelProperty("分页查询系")
    @GetMapping("/getDeptPage")
    public CommonResult getDeptPage(DeptPageReq deptPageReq){
        return deptService.getDeptList(deptPageReq);
    }

    @ApiModelProperty("更新系")
    @PostMapping("/updateDept")
    public CommonResult updateDept(@RequestBody Dept dept){
        return deptService.updateDept(dept);
    }

    @ApiModelProperty("删除系")
    @GetMapping("/deleteDept")
    public CommonResult deleteDept(@Param("deptId") String deptId){
        return deptService.deleteDept(deptId);
    }
}
