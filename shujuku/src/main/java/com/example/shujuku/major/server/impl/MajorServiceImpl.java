package com.example.shujuku.major.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.common.Tool;
import com.example.shujuku.events.bean.Events;
import com.example.shujuku.major.bean.Major;
import com.example.shujuku.major.server.MajorService;
import com.example.shujuku.mapper.MajorMapper;
import com.example.shujuku.req.EventsPageReq;
import com.example.shujuku.req.MajorPageReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    public CommonResult createMajor(Major major){
        boolean insertSuccess = SqlHelper.retBool(majorMapper.insert(major));
        if(!insertSuccess){
            log.info("插入school表失败",major);
            return CommonResult.fail("插入major表失败");
        }
        return CommonResult.success(major);
    }

    @Override
    public CommonResult getMajor(String majorId){
        Major major = majorMapper.selectById(majorId);
        if(major != null){
            return CommonResult.success(major);
        }else return CommonResult.fail("查询major表失败");
    }

    @Override
    public CommonResult getMajorPage(MajorPageReq major){
        Page<Major> page = new Page<>(major.getPageNo(), major.getPageSize());
        LambdaQueryWrapper<Major> queryWrapper = new LambdaQueryWrapper<Major>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(major.getMajorId()), Major::getMajorId, major.getMajorId());
        queryWrapper.like(Tool.isPresent(major.getMajorName()), Major::getMajorName, major.getMajorName());
        queryWrapper.eq(Tool.isPresent(major.getDeptId()), Major::getDeptId, major.getDeptId());
        queryWrapper.like(Tool.isPresent(major.getDeptName()), Major::getDeptName, major.getDeptName());
        queryWrapper.eq(Tool.isPresent(major.getSchoolId()), Major::getSchoolId, major.getSchoolId());
        queryWrapper.like(Tool.isPresent(major.getSchoolName()), Major::getSchoolName, major.getSchoolName());
        queryWrapper.eq(Tool.isPresent(major.getTeacherId()), Major::getTeacherId, major.getTeacherId());
        queryWrapper.like(Tool.isPresent(major.getTeacherName()), Major::getTeacherName, major.getTeacherName());
        //查询
        IPage<Major> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getMajorList(MajorPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Major> majorList = majorMapper.getMajorList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Major> page = new Page<>(pageNo, pageSize);
        page.setRecords(majorList);
        page.setTotal(majorList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateMajor(Major major){
        Major oldMajor = majorMapper.selectById(major.getMajorId());
        Assert.notNull(oldMajor, "修改major表失败，表中查询不到对应majorId的学院");
        if(SqlHelper.retBool(baseMapper.updateById(major))){
            return CommonResult.success(major);
        }else return CommonResult.fail("更新major表失败");
    }

    @Override
    public CommonResult deleteMajor(String majorId){
        Major major = majorMapper.selectById(majorId);
        Assert.notNull(major, "删除major表数据失败，表中查询不到对应majorId的专业");
        if(SqlHelper.retBool(baseMapper.deleteById(majorId))){
            return CommonResult.success(major);
        }else return CommonResult.fail("删除major表数据失败");
    }
}
