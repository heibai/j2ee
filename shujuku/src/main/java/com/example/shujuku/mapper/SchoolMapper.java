package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.SchoolPageReq;
import com.example.shujuku.school.bean.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolMapper extends BaseMapper<School>{

    @Select("SELECT * FROM school WHERE school_name = #{schoolName}")
    School GetSchoolBySchoolName(String schoolName);

    List<School> getSchoolList(SchoolPageReq req);

}