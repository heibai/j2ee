package com.example.shujuku.stupass.server.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.stupass.bean.Stupass;
import com.example.shujuku.mapper.StupassMapper;
import com.example.shujuku.stupass.server.StupassService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.Descriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class StupassServiceImpl extends ServiceImpl<StupassMapper, Stupass> implements StupassService {

    @Autowired
    private StupassMapper stupassMapper;

    @Override
    public CommonResult createStupass(Stupass stupass) {
        boolean insertSuccess = SqlHelper.retBool(stupassMapper.insert(stupass));
        if(!insertSuccess){
            log.info("插入stupass表失败",stupass);
            return CommonResult.fail("插入stupass表失败");
        }
        return CommonResult.success(stupass);
    }

    @Override
    public CommonResult getStupassByStupassId(String stupassId) {
        Stupass stupass = stupassMapper.selectById(stupassId);
        if(stupass != null){
            return CommonResult.success(stupass);
        }else return CommonResult.fail("查询stupass表失败");
    }

    @Override
    public CommonResult updateStupass(Stupass stupass) {
        Stupass oldStupass = stupassMapper.selectById(stupass.getStudentId());
        Assert.notNull(oldStupass, "修改stupass表失败，表中查询不到对应stupassId的数据");
        if(SqlHelper.retBool(baseMapper.updateById(stupass))){
            return CommonResult.success(stupass);
        }else return CommonResult.fail("更新stupass表失败");
    }

    @Override
    public CommonResult deleteStupass(String stupassId){
        Stupass stupass = stupassMapper.selectById(stupassId);
        Assert.notNull(stupass, "删除stupass表数据失败，表中查询不到对应stupassId的数据");
        if(SqlHelper.retBool(baseMapper.deleteById(stupassId))){
            return CommonResult.success(stupass);
        }else return CommonResult.fail("删除stupass表数据失败");
    }

    public CommonResult getStupassStatus(String studentId){
        List<Stupass> StupassList = stupassMapper.GetStupassByStudentId(studentId);
        Integer status = 0;
        LocalDateTime localDateTime = LocalDateTime.now();
        Iterator<Stupass> iterator = StupassList.iterator();
        while(iterator.hasNext()){
            Stupass stupass = iterator.next();
            if(localDateTime.isAfter(stupass.getStuPassBegins())&&localDateTime.isBefore(stupass.getStuPassEnds().plusHours(24))){
                status = 1;
                return CommonResult.success(status);
            }
        }
        return CommonResult.success(status);
    }
}
