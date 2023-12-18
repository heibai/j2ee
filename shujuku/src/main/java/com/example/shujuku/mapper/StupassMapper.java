package com.example.shujuku.mapper;

import com.example.shujuku.stupass.bean.Stupass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StupassMapper extends BaseMapper<Stupass>{

    @Select("SELECT * FROM stupass WHERE student_id = #{studentId}")
    List<Stupass> GetStupassByStudentId(String studentId);
}
