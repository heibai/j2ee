package com.example.shujuku.teacher.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.TeacherPageReq;
import com.example.shujuku.teacher.bean.Teacher;

public interface TeacherService extends IService<Teacher> {

    public CommonResult createTeacher(Teacher teacher);

    public CommonResult getTeacherByTeacherId(String teacherId);

    public CommonResult getTeacherPage(TeacherPageReq teacherPageReq);

    public CommonResult getTeacherList(TeacherPageReq req);

    public CommonResult updateTeacher(Teacher teacher);

    public CommonResult deleteTeacher(String teacherId);
    
}
