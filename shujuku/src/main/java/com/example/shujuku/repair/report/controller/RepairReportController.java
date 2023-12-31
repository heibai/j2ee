package com.example.shujuku.repair.report.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.RepairReportPageReq;
import com.example.shujuku.repair.report.bean.RepairReport;
import com.example.shujuku.repair.report.server.RepairReportService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("报修信息相关接口")
@Slf4j
@RestController
@RequestMapping("repairReport")
@Validated
public class RepairReportController {

    @Autowired
    private RepairReportService repairReportService;

    @ApiModelProperty("创建报修信息")
    @PostMapping("/createRepairReport")
    public CommonResult createRepairReport(@RequestBody RepairReport repairReport){
        return repairReportService.createRepairReport(repairReport);
    }

    @ApiModelProperty("查询报修信息")
    @GetMapping("/getRepairReport")
    public CommonResult getRepairReport(@Param("id") String id){
        return repairReportService.getRepairReportById(id);
    }

    @ApiModelProperty("分页查询报修信息")
    @GetMapping("/getRepairReportPage")
    public CommonResult getRepairReportPage(RepairReportPageReq repairReportPageReq){
        return repairReportService.getRepairReportList(repairReportPageReq);
    }

    @ApiModelProperty("分页查询完整报修信息")
    @GetMapping("/getRepairReportDetailPage")
    public CommonResult getRepairReportDetailPage(RepairReportPageReq repairReportPageReq){
        return repairReportService.getRepairReportDetailList(repairReportPageReq);
    }

    @ApiModelProperty("更新报修信息")
    @PostMapping("/updateRepairReport")
    public CommonResult updateRepairReport(@RequestBody RepairReport repairReport){
        return repairReportService.updateRepairReport(repairReport);
    }

    @ApiModelProperty("删除报修信息")
    @GetMapping("/deleteRepairReport")
    public CommonResult deleteRepairReport(@Param("id") String id){
        return repairReportService.deleteRepairReport(id);
    }
}
