package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.RepairReportPageReq;
import com.example.shujuku.repair.report.bean.RepairReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RepairReportMapper extends BaseMapper<RepairReport>{

    @Select("SELECT * FROM repair_report WHERE id = #{reportId}")
    RepairReport GetRepairReportById(String reportId);

    List<RepairReport> getRepairReportList(RepairReportPageReq req);



}