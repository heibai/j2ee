package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.ResidentPageReq;
import com.example.shujuku.resident.bean.Resident;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResidentMapper extends BaseMapper<Resident>{

    @Select("SELECT * FROM resident WHERE residentId = #{residentId}")
    Resident GetResidentByResidentId(String residentId);

    @Select("SELECT * FROM resident WHERE userId = #{userId}")
    Resident GetResidentByUserId(String userId);


    List<Resident> getResidentListByRoomId(String roomId);

    List<Resident> getResidentList(ResidentPageReq req);

}