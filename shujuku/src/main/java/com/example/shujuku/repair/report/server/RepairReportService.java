package com.example.shujuku.repair.report.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.RepairReportPageReq;
import com.example.shujuku.repair.report.bean.RepairReport;

public interface RepairReportService extends IService<RepairReport> {

    public CommonResult createRepairReport(RepairReport repairReport);

    public CommonResult getRepairReportByReportId(String reportId);

    public CommonResult getRepairReportDetailList(RepairReportPageReq req);

    public CommonResult getRepairReportList(RepairReportPageReq req);

    public CommonResult updateRepairReport(RepairReport repairReport);

    public CommonResult deleteRepairReport(String repairReportId);

}
