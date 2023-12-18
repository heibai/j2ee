package com.example.shujuku.student.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.common.Tool;
import com.example.shujuku.req.StudentPageReq;
import com.example.shujuku.resp.StudentAllInfoPageResp;
import com.example.shujuku.student.bean.Student;
import com.example.shujuku.mapper.StudentMapper;
import com.example.shujuku.student.server.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public CommonResult createStudent(Student student){
        boolean insertSuccess = SqlHelper.retBool(studentMapper.insert(student));
        if(!insertSuccess){
            log.info("插入student表失败",student);
            return CommonResult.fail("插入student表失败");
        }
        return CommonResult.success(student);
    }

    @Override
    public CommonResult getStudentByStudentId(String studentId){
        Student student = studentMapper.selectById(studentId);
        if(student != null){
            return CommonResult.success(student);
        }else return CommonResult.fail("查询student表失败");
    }

    @Override
    public CommonResult getStudentPage(StudentPageReq student) {
        Page<Student> page = new Page<>(student.getPageNo(), student.getPageSize());
        //排序,时间倒序
//        page.addOrder(OrderItem.desc("createdDateTime"));
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<Student>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(student.getStudentId()), Student::getStudentId, student.getStudentId());
        queryWrapper.like(Tool.isPresent(student.getStudentName()), Student::getStudentName, student.getStudentName());
        queryWrapper.eq(Tool.isPresent(student.getGender()), Student::getGender, student.getGender());
        queryWrapper.eq(Tool.isPresent(student.getMajorId()), Student::getMajorId, student.getMajorId());
        queryWrapper.eq(Tool.isPresent(student.getDeptId()), Student::getDeptId, student.getDeptId());
        queryWrapper.eq(Tool.isPresent(student.getSchoolId()), Student::getSchoolId, student.getSchoolId());
        //查询
        IPage<Student> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    @Override
    public CommonResult getStudentList(StudentPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Student> studentList = studentMapper.getStudentList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Student> page = new Page<>(pageNo, pageSize);
        page.setRecords(studentList);
        page.setTotal(studentList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult getStudentAllInfoList(StudentPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<StudentAllInfoPageResp> studentAllInfoList = studentMapper.getStudentAllInfoList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<StudentAllInfoPageResp> page = new Page<>(pageNo, pageSize);
        page.setRecords(studentAllInfoList);
        page.setTotal(studentAllInfoList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateStudent(Student student){
        Student oldStudent = studentMapper.selectById(student.getStudentId());
        Assert.notNull(oldStudent, "修改student表失败，表中查询不到对应studentId的学生");
        if(SqlHelper.retBool(baseMapper.updateById(student))){
            return CommonResult.success(student);
        }else return CommonResult.fail("更新student表失败");
    }

    @Override
    public CommonResult deleteStudent(String studentId){
        Student student = studentMapper.selectById(studentId);
        Assert.notNull(student, "删除student表数据失败，表中查询不到对应studentId的学生");
        if(SqlHelper.retBool(baseMapper.deleteById(studentId))){
            return CommonResult.success(student);
        }else return CommonResult.fail("更新student表失败");
    }

}
