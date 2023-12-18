package com.example.shujuku.teacher.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.TeacherPageReq;
import com.example.shujuku.teacher.bean.Teacher;
import com.example.shujuku.teacher.server.TeacherService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("教师相关接口")
@Slf4j
@RestController
@RequestMapping("teacher")
@Validated
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiModelProperty("创建教师")
    @PostMapping("/createTeacher")
    public CommonResult createTeacher(@RequestBody Teacher teacher){
        return teacherService.createTeacher(teacher);
    }

    @ApiModelProperty("查询教师")
    @GetMapping("/getTeacher")
    public CommonResult getTeacher(@Param("teacherId") String teacherId){
        return teacherService.getTeacherByTeacherId(teacherId);
    }

    @ApiModelProperty("分页查询教师")
    @GetMapping("/getTeacherPage")
    public CommonResult getTeacherPage(TeacherPageReq teacherPageReq){
        return teacherService.getTeacherList(teacherPageReq);
    }

    @ApiModelProperty("更新教师")
    @PostMapping("/updateTeacher")
    public CommonResult updateTeacher(@RequestBody Teacher teacher){
        return teacherService.updateTeacher(teacher);
    }

    @ApiModelProperty("删除教师")
    @GetMapping("/deleteTeacher")
    public CommonResult deleteTeacher(@Param("teacherId") String teacherId){
        return teacherService.deleteTeacher(teacherId);
    }
}
