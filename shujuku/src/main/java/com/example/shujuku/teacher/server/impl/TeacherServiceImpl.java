package com.example.shujuku.teacher.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.common.Tool;
import com.example.shujuku.req.TeacherPageReq;
import com.example.shujuku.teacher.bean.Teacher;
import com.example.shujuku.mapper.TeacherMapper;
import com.example.shujuku.teacher.server.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    protected TeacherMapper teacherMapper;

    @Override
    public CommonResult createTeacher(Teacher teacher){
        boolean insertSuccess = SqlHelper.retBool(teacherMapper.insert(teacher));
        if(!insertSuccess){
            log.info("插入teacher表失败",teacher);
            return CommonResult.fail("插入teacher表失败");
        }
        return CommonResult.success(teacher);
    }

    @Override
    public CommonResult getTeacherByTeacherId(String teacherId){
        Teacher teacher = teacherMapper.selectById(teacherId);
        if(teacher != null){
            return CommonResult.success(teacher);
        }else return CommonResult.fail("查询teacher表失败");
    }

    @Override
    public CommonResult getTeacherPage(TeacherPageReq teacher) {
        Page<Teacher> page = new Page<>(teacher.getPageNo(), teacher.getPageSize());
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<Teacher>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(teacher.getTeacherId()), Teacher::getTeacherId, teacher.getTeacherId());
        queryWrapper.like(Tool.isPresent(teacher.getTeacherName()), Teacher::getTeacherName, teacher.getTeacherName());
        queryWrapper.eq(Tool.isPresent(teacher.getPhone()), Teacher::getPhone, teacher.getPhone());
        queryWrapper.like(Tool.isPresent(teacher.getWorkPlace()), Teacher::getWorkPlace, teacher.getWorkPlace());
        queryWrapper.eq(Tool.isPresent(teacher.getWorkDay()), Teacher::getWorkDay, teacher.getWorkDay());
        queryWrapper.eq(Tool.isPresent(teacher.getDeptId()), Teacher::getDeptId, teacher.getDeptId());
        //查询
        IPage<Teacher> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getTeacherList(TeacherPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Teacher> teacherList = teacherMapper.getTeacherList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        page.setRecords(teacherList);
        page.setTotal(teacherList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateTeacher(Teacher teacher){
        Teacher oldTeacher = teacherMapper.selectById(teacher.getTeacherId());
        Assert.notNull(oldTeacher, "修改teacher表失败，表中查询不到对应teacherId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(teacher))){
            return CommonResult.success(teacher);
        }else return CommonResult.fail("更新teacher表失败");
    }

    @Override
    public CommonResult deleteTeacher(String teacherId){
        Teacher teacher = teacherMapper.selectById(teacherId);
        Assert.notNull(teacher, "删除teacher表数据失败，表中查询不到对应teacherId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(teacherId))){
            return CommonResult.success(teacher);
        }else return CommonResult.fail("删除teacher表数据失败");
    }
}