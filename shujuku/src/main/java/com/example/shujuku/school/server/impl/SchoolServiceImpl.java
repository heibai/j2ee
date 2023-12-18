package com.example.shujuku.school.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.common.Tool;
import com.example.shujuku.events.bean.Events;
import com.example.shujuku.req.SchoolPageReq;
import com.example.shujuku.school.bean.School;
import com.example.shujuku.mapper.SchoolMapper;
import com.example.shujuku.school.server.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    public CommonResult createSchool(School school){
        boolean insertSuccess = SqlHelper.retBool(schoolMapper.insert(school));
        if(!insertSuccess){
            log.info("插入school表失败",school);
            return CommonResult.fail("插入school表失败");
        }
        return CommonResult.success(school);
    }

    @Override
    public CommonResult getSchoolBySchoolId(String schoolId){
        School school = schoolMapper.selectById(schoolId);
        if(school != null){
            return CommonResult.success(school);
        }else return CommonResult.fail("查询school表失败");
    }

    @Override
    public CommonResult getSchoolBySchoolName(String schoolName){
        School school = schoolMapper.GetSchoolBySchoolName(schoolName);
        if(school != null){
            return CommonResult.success(school);
        }else return CommonResult.fail("查询school表失败");
    }

    @Override
    public CommonResult getSchoolPage(SchoolPageReq school) {
        Page<School> page = new Page<>(school.getPageNo(), school.getPageSize());
        LambdaQueryWrapper<School> queryWrapper = new LambdaQueryWrapper<School>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(school.getSchoolId()), School::getSchoolId, school.getSchoolId());
        queryWrapper.like(Tool.isPresent(school.getSchoolName()), School::getSchoolName, school.getSchoolName());
        queryWrapper.eq(Tool.isPresent(school.getSchoolLocationId()), School::getSchoolLocationId, school.getSchoolLocationId());
        queryWrapper.like(Tool.isPresent(school.getSchoolLocation()), School::getSchoolLocation, school.getSchoolLocation());
        //查询
        IPage<School> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getSchoolList(SchoolPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<School> schoolList = schoolMapper.getSchoolList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<School> page = new Page<>(pageNo, pageSize);
        page.setRecords(schoolList);
        page.setTotal(schoolList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateSchool(School school){
        School oldSchool = schoolMapper.selectById(school.getSchoolId());
        Assert.notNull(oldSchool, "修改school表失败，表中查询不到对应schoolId的学院");
        if(SqlHelper.retBool(baseMapper.updateById(school))){
            return CommonResult.success(school);
        }else return CommonResult.fail("更新school表失败");
    }

    @Override
    public CommonResult deleteSchool(String schoolId){
        School school = schoolMapper.selectById(schoolId);
        Assert.notNull(school, "删除school表数据失败，表中查询不到对应schoolId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(schoolId))){
            return CommonResult.success(school);
        }else return CommonResult.fail("删除school表数据失败");
    }
}
