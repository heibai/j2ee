package com.example.shujuku.repair.report.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.common.Tool;
import com.example.shujuku.req.RepairReportPageReq;
import com.example.shujuku.repair.report.bean.RepairReport;
import com.example.shujuku.mapper.RepairReportMapper;
import com.example.shujuku.repair.report.server.RepairReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class RepairReportServiceImpl extends ServiceImpl<RepairReportMapper, RepairReport> implements RepairReportService {

    @Autowired
    protected RepairReportMapper repairReportMapper;

    @Override
    public CommonResult createRepairReport(RepairReport repairReport){
        boolean insertSuccess = SqlHelper.retBool(repairReportMapper.insert(repairReport));
        if(!insertSuccess){
            log.info("插入repairReport表失败",repairReport);
            return CommonResult.fail("插入repairReport表失败");
        }
        return CommonResult.success(repairReport);
    }

    @Override
    public CommonResult getRepairReportByReportId(String reportId){
        RepairReport repairReport = repairReportMapper.selectById(reportId);
        if(repairReport != null){
            return CommonResult.success(repairReport);
        }else return CommonResult.fail("查询repairReport表失败");
    }

    @Override
    public CommonResult getRepairReportPage(RepairReportPageReq repairReport) {
        Page<RepairReport> page = new Page<>(repairReport.getPageNo(), repairReport.getPageSize());
        LambdaQueryWrapper<RepairReport> queryWrapper = new LambdaQueryWrapper<RepairReport>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(repairReport.getReportId()), RepairReport::getReportId, repairReport.getReportId());
        queryWrapper.eq(Tool.isPresent(repairReport.getRepairerId()), RepairReport::getRepairerId, repairReport.getRepairerId());
//        queryWrapper.like(Tool.isPresent(repairReport.getName()), RepairReport::getName, repairReport.getName());
        queryWrapper.eq(Tool.isPresent(repairReport.getStatus()), RepairReport::getStatus, repairReport.getStatus());
//
        //        //查询
        IPage<RepairReport> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getRepairReportList(RepairReportPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<RepairReport> repairReportList = repairReportMapper.getRepairReportList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<RepairReport> page = new Page<>(pageNo, pageSize);
        page.setRecords(repairReportList);
        page.setTotal(repairReportList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateRepairReport(RepairReport repairReport){
        RepairReport oldRepairReport = repairReportMapper.selectById(repairReport.getReportId());
        Assert.notNull(oldRepairReport, "修改repairReport表失败，表中查询不到对应repairReportId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(repairReport))){
            return CommonResult.success(repairReport);
        }else return CommonResult.fail("更新repairReport表失败");
    }

    @Override
    public CommonResult deleteRepairReport(String reportId){
        RepairReport repairReport = repairReportMapper.selectById(reportId);
        Assert.notNull(repairReport, "删除repairReport表数据失败，表中查询不到对应repairReportId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(reportId))){
            return CommonResult.success(repairReport);
        }else return CommonResult.fail("删除repairReport表数据失败");
    }
}