package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.TeacherPageReq;
import com.example.shujuku.teacher.bean.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher>{

    @Select("SELECT * FROM teacher WHERE teacherId = #{teacherId}")
    Teacher GetTeacherByTeacherId(String teacherId);

    List<Teacher> getTeacherList(TeacherPageReq req);

}