package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.FeesPageReq;
import com.example.shujuku.fees.bean.Fees;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeesMapper extends BaseMapper<Fees>{

    @Select("SELECT * FROM fees WHERE feesId = #{feesId}")
    Fees GetFeesByFeesId(String feesId);

    List<Fees> GetFeesByRoomId(String roomId);

    List<Fees> getFeesList(FeesPageReq req);

}