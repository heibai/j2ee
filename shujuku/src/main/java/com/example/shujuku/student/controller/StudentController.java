package com.example.shujuku.student.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.StudentPageReq;
import com.example.shujuku.student.bean.Student;
import com.example.shujuku.student.server.StudentService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api("学生相关接口")
@RequestMapping("/student")
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiModelProperty("创建学生")
    @PostMapping("/createStudent")
    public CommonResult createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    @ApiModelProperty("学号查询学生")
    @GetMapping("/getStudent")
    public CommonResult getStudent(@Param("studentId") String studentId){
        return studentService.getStudentByStudentId(studentId);
    }

    @ApiModelProperty("获取学生列表")
    @GetMapping("/getStudentPage")
    public CommonResult getStudentPage(StudentPageReq studentPageReq){
        return studentService.getStudentList(studentPageReq);
    }

    @ApiModelProperty("获取学生完整信息列表")
    @GetMapping("/getStudentAllInfoPage")
    public CommonResult getStudentAllInfoPage(StudentPageReq studentPageReq){
        return studentService.getStudentAllInfoList(studentPageReq);
    }


    @ApiModelProperty("更新学生数据")
    @PostMapping("/updateStudent")
    public CommonResult updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @ApiModelProperty("删除学生数据")
    @GetMapping("/deleteStudent")
    public CommonResult deleteStudent(@Param("studentId") String studentId){
        return studentService.deleteStudent(studentId);
    }

}
