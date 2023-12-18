package com.example.shujuku.dept.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.common.Tool;
import com.example.shujuku.dept.bean.Dept;
import com.example.shujuku.mapper.DeptMapper;
import com.example.shujuku.dept.server.DeptService;
import com.example.shujuku.req.DeptPageReq;
import com.example.shujuku.student.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public CommonResult createDept(Dept dept) {boolean insertSuccess = SqlHelper.retBool(deptMapper.insert(dept));
        if (!insertSuccess) {
            log.info("插入dept表失败", dept);
            return CommonResult.fail("插入dept表失败");
        }
        return CommonResult.success(dept);
    }

    @Override
    public CommonResult getDeptByDeptId(String deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (dept != null) {
            return CommonResult.success(dept);
        } else return CommonResult.fail("查询dept表失败");
    }

    @Override
    public CommonResult getDeptPage(DeptPageReq dept) {
        Page<Dept> page = new Page<>(dept.getPageNo(), dept.getPageSize());
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<Dept>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(dept.getDeptId()), Dept::getDeptId, dept.getDeptId());
        queryWrapper.like(Tool.isPresent(dept.getDeptName()), Dept::getDeptName, dept.getDeptName());
        queryWrapper.eq(Tool.isPresent(dept.getSchoolId()), Dept::getSchoolId, dept.getSchoolId());
        IPage<Dept> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    @Override
    public CommonResult getDeptList(DeptPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Dept> deptList = deptMapper.getDeptList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Dept> page = new Page<>(pageNo, pageSize);
        page.setRecords(deptList);
        page.setTotal(deptList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateDept(Dept dept) {
        Dept oldDept = deptMapper.selectById(dept.getDeptId());
        Assert.notNull(oldDept, "修改dept表失败，表中查询不到对应deptId的系");
        if (SqlHelper.retBool(baseMapper.updateById(dept))) {
            return CommonResult.success(dept);
        } else return CommonResult.fail("更新dept表失败");
    }

    @Override
    public CommonResult deleteDept(String deptId){
        Dept dept = deptMapper.selectById(deptId);
        Assert.notNull(dept, "删除dept表数据失败，表中查询不到对应deptId的系");
        if(SqlHelper.retBool(baseMapper.deleteById(deptId))){
            return CommonResult.success(dept);
        }else return CommonResult.fail("更新dept表失败");
    }
}
