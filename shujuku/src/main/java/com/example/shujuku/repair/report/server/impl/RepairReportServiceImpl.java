package com.example.shujuku.repair.report.server.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.mapper.UsersMapper;
import com.example.shujuku.req.RepairReportPageReq;
import com.example.shujuku.repair.report.bean.RepairReport;
import com.example.shujuku.mapper.RepairReportMapper;
import com.example.shujuku.repair.report.server.RepairReportService;
import com.example.shujuku.users.bean.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Service
public class RepairReportServiceImpl extends ServiceImpl<RepairReportMapper, RepairReport> implements RepairReportService {

    @Autowired
    protected RepairReportMapper repairReportMapper;
    @Autowired
    protected UsersMapper usersMapper;

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
    public CommonResult getRepairReportById(String id){
        RepairReport repairReport = repairReportMapper.selectById(id);
        if(repairReport != null){
            return CommonResult.success(repairReport);
        }else return CommonResult.fail("查询repairReport表失败");
    }

    @Override
    public CommonResult getRepairReportDetailList(RepairReportPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        //分页查询报修信息
        List<RepairReport> repairReportList = repairReportMapper.getRepairReportList(req);
        ListIterator<RepairReport> repairReportListIterator = repairReportList.listIterator();
        while(repairReportListIterator.hasNext()){
            HashMap<String,Object> resultMap = new HashMap<String,Object>();
            RepairReport repairReport = repairReportListIterator.next();
            //对每一个报修信息，查询其报修者和维修者的信息
            Users reporter = usersMapper.GetUsersByUserId(repairReport.getReporterId());
            Users repairer = usersMapper.GetUsersByUserId(repairReport.getRepairerId());
            //把消息装入同一个map，再把map装入list返回
            resultMap.put("repairReport",repairReport);
            resultMap.put("reporter",reporter);
            resultMap.put("repairer",repairer);
            resultList.add(resultMap);
        }
        Page<HashMap<String,Object>> page = new Page<>(pageNo, pageSize);
        page.setRecords(resultList);
        page.setTotal(resultList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult getRepairReportList(RepairReportPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        //分页查询维修信息
        List<RepairReport> repairReportList = repairReportMapper.getRepairReportList(req);
        Page<RepairReport> page = new Page<>(pageNo, pageSize);
        page.setRecords(repairReportList);
        page.setTotal(repairReportList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateRepairReport(RepairReport repairReport){
        RepairReport oldRepairReport = repairReportMapper.selectById(repairReport.getId());
        Assert.notNull(oldRepairReport, "修改repairReport表失败，表中查询不到对应Id的报修信息");
        if(SqlHelper.retBool(baseMapper.updateById(repairReport))){
            return CommonResult.success(repairReport);
        }else return CommonResult.fail("更新repairReport表失败");
    }

    @Override
    public CommonResult deleteRepairReport(String id){
        RepairReport repairReport = repairReportMapper.selectById(id);
        Assert.notNull(repairReport, "删除repairReport表数据失败，表中查询不到对应Id的报修信息");
        if(SqlHelper.retBool(baseMapper.deleteById(id))){
            return CommonResult.success(repairReport);
        }else return CommonResult.fail("删除repairReport表数据失败");
    }
}