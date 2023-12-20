package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.ComplaintPageReq;
import com.example.shujuku.complaint.bean.Complaint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComplaintMapper extends BaseMapper<Complaint>{

    @Select("SELECT * FROM complaint WHERE complaintId = #{complaintId}")
    Complaint GetComplaintByComplaintId(String complaintId);

    List<Complaint> getComplaintList(ComplaintPageReq req);

}