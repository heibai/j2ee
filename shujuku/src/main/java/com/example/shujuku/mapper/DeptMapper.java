package com.example.shujuku.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.dept.bean.Dept;
import com.example.shujuku.req.DeptPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    @Select("SELECT * FROM dept WHERE deptId = #{deptId}")
    Dept GetDeptByStudentId(String deptId);

    List<Dept> getDeptList(DeptPageReq req);

}
