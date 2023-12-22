package com.example.shujuku.fees.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.common.SnowflakeIdGenerator;
import com.example.shujuku.common.Tool;
import com.example.shujuku.mapper.RoomMapper;
import com.example.shujuku.req.FeesPageReq;
import com.example.shujuku.fees.bean.Fees;
import com.example.shujuku.mapper.FeesMapper;
import com.example.shujuku.fees.server.FeesService;
import com.example.shujuku.room.bean.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Service
public class FeesServiceImpl extends ServiceImpl<FeesMapper, Fees> implements FeesService {

    @Autowired
    protected FeesMapper feesMapper;
    @Autowired
    protected RoomMapper roomMapper;
    @Autowired
    protected SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public CommonResult createResidentFees(String price, String deadline,String type){
        List<Room> roomList = roomMapper.getResidentedRooms();
        ListIterator<Room> roomListIterator = roomList.listIterator();
        while(roomListIterator.hasNext()){
            Room room = roomListIterator.next();
            Fees fees = new Fees();
            Long id = snowflakeIdGenerator.nextId();
            fees.setFeesId(id.toString());
            fees.setMessage("住宿费");
            fees.setPrice(Double.valueOf(price));
            fees.setStatus(String.valueOf(1));
            fees.setType(type);
            fees.setDeadline(LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            fees.setRoomId(room.getRoomId());
            feesMapper.insert(fees);
        }
        return CommonResult.success("新建住宿费成功");
    }

    @Override
    public CommonResult createFees(Fees fees){
        boolean insertSuccess = SqlHelper.retBool(feesMapper.insert(fees));
        if(!insertSuccess){
            log.info("插入fees表失败",fees);
            return CommonResult.fail("插入fees表失败");
        }
        return CommonResult.success(fees);
    }

    @Override
    public CommonResult getFeesByFeesId(String feesId){
        Fees fees = feesMapper.selectById(feesId);
        if(fees != null){
            return CommonResult.success(fees);
        }else return CommonResult.fail("查询fees表失败");
    }

    @Override
    public CommonResult getFeesPage(FeesPageReq fees) {
        Page<Fees> page = new Page<>(fees.getPageNo(), fees.getPageSize());
        LambdaQueryWrapper<Fees> queryWrapper = new LambdaQueryWrapper<Fees>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(fees.getFeesId()), Fees::getFeesId, fees.getFeesId());
//        queryWrapper.like(Tool.isPresent(fees.getName()), Fees::getName, fees.getName());
        queryWrapper.eq(Tool.isPresent(fees.getRoomId()), Fees::getRoomId, fees.getRoomId());
        queryWrapper.eq(Tool.isPresent(fees.getType()), Fees::getType, fees.getType());
        queryWrapper.eq(Tool.isPresent(fees.getStatus()), Fees::getStatus, fees.getStatus());
//
        //        //查询
        IPage<Fees> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getFeesList(FeesPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Fees> feesList = feesMapper.getFeesList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Fees> page = new Page<>(pageNo, pageSize);
        page.setRecords(feesList);
        page.setTotal(feesList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateFees(Fees fees){
        Fees oldFees = feesMapper.selectById(fees.getId());
        Assert.notNull(oldFees, "修改fees表失败，表中查询不到对应feesId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(fees))){
            return CommonResult.success(fees);
        }else return CommonResult.fail("更新fees表失败");
    }

    @Override
    public CommonResult deleteFees(String feesId){
        Fees fees = feesMapper.selectById(feesId);
        Assert.notNull(fees, "删除fees表数据失败，表中查询不到对应feesId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(feesId))){
            return CommonResult.success(fees);
        }else return CommonResult.fail("删除fees表数据失败");
    }
}