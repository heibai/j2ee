package com.example.shujuku.events.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.common.Tool;
import com.example.shujuku.events.bean.Events;
import com.example.shujuku.mapper.EventsMapper;
import com.example.shujuku.events.server.EventsService;
import com.example.shujuku.mapper.StupassMapper;
import com.example.shujuku.req.EventsPageReq;
import com.example.shujuku.stupass.bean.Stupass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EventsServiceImpl extends ServiceImpl<EventsMapper, Events> implements EventsService {

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private StupassMapper stupassMapper;

    @Override
    public CommonResult createEvents(Events events){
        if(Tool.isPresent(events.getEventStatus())){
            events.setEventStatus("1");
        }
        boolean insertSuccess = SqlHelper.retBool(eventsMapper.insert(events));
        if(!insertSuccess){
            log.info("插入events表失败",events);
            return CommonResult.fail("插入events表失败");
        }
        return CommonResult.success(events);
    }

    @Override
    public CommonResult getEvents(String eventId){
        Events events = eventsMapper.selectById(eventId);
        if(events != null){
            return CommonResult.success(events);
        }else return CommonResult.fail("查询events表失败");
    }

    @Override
    public CommonResult getEventsPage(EventsPageReq events) {
        Page<Events> page = new Page<>(events.getPageNo(), events.getPageSize());
        //排序,时间倒序
        page.addOrder(OrderItem.desc("createdDatetime"));
        LambdaQueryWrapper<Events> queryWrapper = new LambdaQueryWrapper<Events>();
        //多条件匹配查询
        queryWrapper.eq(Tool.isPresent(events.getEventId()), Events::getEventId, events.getEventId());
        queryWrapper.eq(Tool.isPresent(events.getStudentId()), Events::getStudentId, events.getStudentId());
        queryWrapper.eq(Tool.isPresent(events.getMajorId()), Events::getMajorId, events.getMajorId());
        queryWrapper.eq(Tool.isPresent(events.getDeptId()), Events::getDeptId, events.getDeptId());
        queryWrapper.eq(Tool.isPresent(events.getSchoolId()), Events::getSchoolId, events.getSchoolId());
        queryWrapper.eq(Tool.isPresent(events.getEventStatus()), Events::getEventStatus, events.getEventStatus());
        //查询
        IPage<Events> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getEventsList(EventsPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Events> eventsList = eventsMapper.getEventsList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Events> page = new Page<>(pageNo, pageSize);
        page.setRecords(eventsList);
        page.setTotal(eventsList.size());
        return CommonResult.success(page);
    }

    public CommonResult getEventsAllInfoList(EventsPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Events> eventsList = eventsMapper.getEventsAllInfoList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Events> page = new Page<>(pageNo, pageSize);
        page.setRecords(eventsList);
        page.setTotal(eventsList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateEvents(Events events){
        Events oldEvents = eventsMapper.selectById(events.getEventId());
        Assert.notNull(oldEvents, "修改events表失败，表中查询不到对应eventId的申请");
        if(SqlHelper.retBool(baseMapper.updateById(events))){
            return CommonResult.success(events);
        }else return CommonResult.fail("更新events表失败");
    }

    @Override
    public CommonResult deptConfimEvents(String eventId, String pass){
        Events events = eventsMapper.selectById(eventId);
        Assert.notNull(events, "申请审批失败，表中查询不到对应eventId的申请");
        if(events.getEventArea().equals("1")){//出校不出市
            if(pass.equals("1")){//审批通过
                events.setEventStatus("2");//辅导员审批完成，不用学院审批
                Stupass stupass = new Stupass();
                stupass.setStudentId(events.getStudentId());
                stupass.setStuPassBegins(events.getEventBegins());
                stupass.setStuPassEnds(events.getEventEnds());
                boolean insertSuccess = SqlHelper.retBool(stupassMapper.insert(stupass));
                if(!insertSuccess){
                    log.info("插入stupass表失败",stupass);
                    return CommonResult.fail("插入stupass表失败");
                }
            }else{
                events.setEventStatus("5");//辅导员审批不通过
            }
        }else if(events.getEventArea().equals("2")||events.getEventArea().equals("3")){//出市/出省
            if(pass.equals("1")){//审批通过
                events.setEventStatus("3");//辅导员审批完成，待学院审批
            }else{
                events.setEventStatus("5");//辅导员审批不通过
            }
        }
        events.setDeptUpdatedDatetime(LocalDateTime.now());
        if(SqlHelper.retBool(baseMapper.updateById(events))){
            return CommonResult.success(events);
        }else return CommonResult.fail("更新events表失败");
    }

    @Override
    public CommonResult schoolConfimEvents(String eventId, String pass){
        Events events = eventsMapper.selectById(eventId);
        Assert.notNull(events, "申请审批失败，表中查询不到对应eventId的申请");
        if(pass.equals("1")){//审批通过
            events.setEventStatus("4");//审批完成
            Stupass stupass = new Stupass();
            stupass.setStudentId(events.getStudentId());
            stupass.setStuPassBegins(events.getEventBegins());
            stupass.setStuPassEnds(events.getEventEnds());
            boolean insertSuccess = SqlHelper.retBool(stupassMapper.insert(stupass));
            if(!insertSuccess){
                log.info("插入stupass表失败",stupass);
                return CommonResult.fail("插入stupass表失败");
            }
        }else{
            events.setEventStatus("6");//学院审批不通过
        }
        events.setSchoolUpdatedDatetime(LocalDateTime.now());
        if(SqlHelper.retBool(baseMapper.updateById(events))){
            return CommonResult.success(events);
        }else return CommonResult.fail("更新events表失败");
    }

    @Override
    public CommonResult deleteEvents(String eventsId){
        Events events = eventsMapper.selectById(eventsId);
        Assert.notNull(events, "删除events表数据失败，表中查询不到对应eventId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(eventsId))){
            return CommonResult.success(events);
        }else return CommonResult.fail("更新events表失败");
    }
}
