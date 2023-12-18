package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.StudentPageReq;
import com.example.shujuku.resp.StudentAllInfoPageResp;
import com.example.shujuku.student.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student>{

    @Select("SELECT * FROM student WHERE studentId = #{studentId}")
    Student GetStudentByStudentId(String studentId);

    List<Student> getStudentList(StudentPageReq req);

    List<StudentAllInfoPageResp> getStudentAllInfoList(StudentPageReq req);
}