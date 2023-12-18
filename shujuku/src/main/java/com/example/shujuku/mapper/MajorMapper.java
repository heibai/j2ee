package com.example.shujuku.mapper;

import com.example.shujuku.major.bean.Major;
import com.example.shujuku.req.MajorPageReq;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
public interface MajorMapper extends BaseMapper<Major>{

    List<Major> getMajorList(MajorPageReq req);

}
