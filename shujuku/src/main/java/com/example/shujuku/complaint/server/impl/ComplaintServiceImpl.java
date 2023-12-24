package com.example.shujuku.complaint.server.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.req.ComplaintPageReq;
import com.example.shujuku.complaint.bean.Complaint;
import com.example.shujuku.mapper.ComplaintMapper;
import com.example.shujuku.complaint.server.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

    @Autowired
    protected ComplaintMapper complaintMapper;

    @Override
    public CommonResult createComplaint(Complaint complaint){
        boolean insertSuccess = SqlHelper.retBool(complaintMapper.insert(complaint));
        if(!insertSuccess){
            log.info("插入complaint表失败",complaint);
            return CommonResult.fail("插入complaint表失败");
        }
        return CommonResult.success(complaint);
    }

    @Override
    public CommonResult getComplaintById(String id){
        Complaint complaint = complaintMapper.selectById(id);
        if(complaint != null){
            return CommonResult.success(complaint);
        }else return CommonResult.fail("查询complaint表失败");
    }

    @Override
    public CommonResult getComplaintList(ComplaintPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Complaint> complaintList = complaintMapper.getComplaintList(req);
        Page<Complaint> page = new Page<>(pageNo, pageSize);
        page.setRecords(complaintList);
        page.setTotal(complaintList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateComplaint(Complaint complaint){
        Complaint oldComplaint = complaintMapper.selectById(complaint.getId());
        Assert.notNull(oldComplaint, "修改complaint表失败，表中查询不到对应id的投诉信息");
        if(SqlHelper.retBool(baseMapper.updateById(complaint))){
            return CommonResult.success(complaint);
        }else return CommonResult.fail("更新complaint表失败");
    }

    @Override
    public CommonResult deleteComplaint(String id){
        Complaint complaint = complaintMapper.selectById(id);
        Assert.notNull(complaint, "删除complaint表数据失败，表中查询不到对应id的投诉信息");
        if(SqlHelper.retBool(baseMapper.deleteById(id))){
            return CommonResult.success(complaint);
        }else return CommonResult.fail("删除complaint表数据失败");
    }
}