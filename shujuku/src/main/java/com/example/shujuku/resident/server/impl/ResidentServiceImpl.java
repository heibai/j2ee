package com.example.shujuku.resident.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.common.Tool;
import com.example.shujuku.req.ResidentPageReq;
import com.example.shujuku.resident.bean.Resident;
import com.example.shujuku.mapper.ResidentMapper;
import com.example.shujuku.resident.server.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class ResidentServiceImpl extends ServiceImpl<ResidentMapper, Resident> implements ResidentService {

    @Autowired
    protected ResidentMapper residentMapper;

    @Override
    public CommonResult createResident(Resident resident){
        boolean insertSuccess = SqlHelper.retBool(residentMapper.insert(resident));
        if(!insertSuccess){
            log.info("插入resident表失败",resident);
            return CommonResult.fail("插入resident表失败");
        }
        return CommonResult.success(resident);
    }

    @Override
    public CommonResult getResidentByResidentId(String residentId){
        Resident resident = residentMapper.selectById(residentId);
        if(resident != null){
            return CommonResult.success(resident);
        }else return CommonResult.fail("查询resident表失败");
    }

    @Override
    public CommonResult getResidentPage(ResidentPageReq resident) {
        Page<Resident> page = new Page<>(resident.getPageNo(), resident.getPageSize());
        LambdaQueryWrapper<Resident> queryWrapper = new LambdaQueryWrapper<Resident>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(resident.getUserId()), Resident::getUserId, resident.getUserId());
//        queryWrapper.like(Tool.isPresent(resident.getName()), Resident::getName, resident.getName());
        queryWrapper.eq(Tool.isPresent(resident.getRoomId()), Resident::getRoomId, resident.getRoomId());
        queryWrapper.eq(Tool.isPresent(resident.getBuildingId()), Resident::getBuildingId, resident.getBuildingId());
        queryWrapper.eq(Tool.isPresent(resident.getStatus()), Resident::getStatus, resident.getStatus());
//
        //        //查询
        IPage<Resident> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getResidentList(ResidentPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Resident> residentList = residentMapper.getResidentList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Resident> page = new Page<>(pageNo, pageSize);
        page.setRecords(residentList);
        page.setTotal(residentList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateResident(Resident resident){
        Resident oldResident = residentMapper.selectById(resident.getUserId());
        Assert.notNull(oldResident, "修改resident表失败，表中查询不到对应residentId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(resident))){
            return CommonResult.success(resident);
        }else return CommonResult.fail("更新resident表失败");
    }

    @Override
    public CommonResult deleteResident(String residentId){
        Resident resident = residentMapper.selectById(residentId);
        Assert.notNull(resident, "删除resident表数据失败，表中查询不到对应residentId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(residentId))){
            return CommonResult.success(resident);
        }else return CommonResult.fail("删除resident表数据失败");
    }
}