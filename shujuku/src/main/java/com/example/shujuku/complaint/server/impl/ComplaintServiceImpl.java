package com.example.shujuku.complaint.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.common.Tool;
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
    public CommonResult getComplaintByComplaintId(String complaintId){
        Complaint complaint = complaintMapper.GetComplaintByComplaintId(complaintId);
        if(complaint != null){
            return CommonResult.success(complaint);
        }else return CommonResult.fail("查询complaint表失败");
    }

    @Override
    public CommonResult getComplaintPage(ComplaintPageReq complaint) {
        Page<Complaint> page = new Page<>(complaint.getPageNo(), complaint.getPageSize());
        LambdaQueryWrapper<Complaint> queryWrapper = new LambdaQueryWrapper<Complaint>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(complaint.getComplaintId()), Complaint::getComplaintId, complaint.getComplaintId());
//        queryWrapper.like(Tool.isPresent(complaint.getName()), Complaint::getName, complaint.getName());
        queryWrapper.eq(Tool.isPresent(complaint.getStatus()), Complaint::getStatus, complaint.getStatus());
//
        //        //查询
        IPage<Complaint> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getComplaintList(ComplaintPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Complaint> complaintList = complaintMapper.getComplaintList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Complaint> page = new Page<>(pageNo, pageSize);
        page.setRecords(complaintList);
        page.setTotal(complaintList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateComplaint(Complaint complaint){
        Complaint oldComplaint = complaintMapper.selectById(complaint.getId());
        Assert.notNull(oldComplaint, "修改complaint表失败，表中查询不到对应complaintId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(complaint))){
            return CommonResult.success(complaint);
        }else return CommonResult.fail("更新complaint表失败");
    }

    @Override
    public CommonResult deleteComplaint(String complaintId){
        Complaint complaint = complaintMapper.GetComplaintByComplaintId(complaintId);
        Assert.notNull(complaint, "删除complaint表数据失败，表中查询不到对应complaintId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(complaintId))){
            return CommonResult.success(complaint);
        }else return CommonResult.fail("删除complaint表数据失败");
    }
}