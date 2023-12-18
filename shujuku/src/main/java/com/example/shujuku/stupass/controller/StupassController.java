package com.example.shujuku.stupass.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.stupass.bean.Stupass;
import com.example.shujuku.stupass.server.StupassService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("通行码相关接口")
@Slf4j
@RestController
@RequestMapping("stupass")
@Validated
public class StupassController {
    @Autowired
    private StupassService stupassService;

    @ApiModelProperty("创建通行码")
    @PostMapping("/createStupass")
    public CommonResult createStupass(@RequestBody Stupass stupass){
        return stupassService.createStupass(stupass);
    }

    @ApiModelProperty("查询通行码")
    @GetMapping("/getStupass")
    public CommonResult getStupass(@Param("stupassId") String stupassId){
        return stupassService.getStupassByStupassId(stupassId);
    }

    @ApiModelProperty("更新通行码")
    @PostMapping("/updateStupass")
    public CommonResult updateStupass(@RequestBody Stupass stupass){
        return stupassService.updateStupass(stupass);
    }

    @ApiModelProperty("删除通信码")
    @GetMapping("/deleteStupass")
    public CommonResult deleteStupass(@Param("stupassId") String stupassId){
        return stupassService.deleteStupass(stupassId);
    }

    @ApiModelProperty("查询通行码状态")
    @GetMapping("/getStupassStatus")
    public CommonResult getStupassStatus(@Param("studentId") String studentId){
        return stupassService.getStupassStatus(studentId);
    }
}
