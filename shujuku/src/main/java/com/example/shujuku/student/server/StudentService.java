package com.example.shujuku.student.server;



import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.StudentPageReq;
import com.example.shujuku.student.bean.Student;

import java.util.List;

public interface StudentService extends IService<Student> {

    public CommonResult createStudent(Student student);

    public CommonResult getStudentByStudentId(String studentId);

    public CommonResult getStudentPage(StudentPageReq studentPageReq);

    public CommonResult getStudentList(StudentPageReq req);

    public CommonResult getStudentAllInfoList(StudentPageReq req);

    public CommonResult updateStudent(Student student);

    public CommonResult deleteStudent(String studentId);
}
