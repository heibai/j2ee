package com.example.shujuku.school.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.SchoolPageReq;
import com.example.shujuku.school.bean.School;
import com.example.shujuku.school.server.SchoolService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("学院相关接口")
@Slf4j
@RestController
@RequestMapping("/school")
@Validated
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @ApiModelProperty("创建学院")
    @PostMapping("/createSchool")
    public CommonResult createSchool(@RequestBody School school){
        return schoolService.createSchool(school);
    }

    @ApiModelProperty("学院号查询学院")
    @GetMapping("/getSchoolBySchoolId")
    public CommonResult getSchoolBySchoolId(@Param("schoolId") String schoolId){
        return schoolService.getSchoolBySchoolId(schoolId);
    }

    @ApiModelProperty("学院名查询学院")
    @GetMapping("/getSchoolBySchoolName")
    public CommonResult getSchoolBySchoolName(@Param("schoolName") String schoolName){
        return schoolService.getSchoolBySchoolName(schoolName);
    }

    @ApiModelProperty("分页查询学院")
    @GetMapping("/getSchoolPage")
    public CommonResult getSchoolPage(SchoolPageReq schoolPageReq){
        return schoolService.getSchoolList(schoolPageReq);
    }

    @ApiModelProperty("更新学院")
    @PostMapping("/updateSchool")
    public CommonResult updateSchool(@RequestBody School school){
        return schoolService.updateSchool(school);
    }

    @ApiModelProperty("删除学院")
    @GetMapping("/deleteSchool")
    public CommonResult deleteSchool(@Param("schoolId") String schoolId){
        return schoolService.deleteSchool(schoolId);
    }
}
