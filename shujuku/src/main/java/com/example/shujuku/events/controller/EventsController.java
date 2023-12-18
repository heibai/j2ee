package com.example.shujuku.events.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.events.bean.Events;
import com.example.shujuku.events.server.EventsService;
import com.example.shujuku.req.EventsPageReq;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("申请事件相关接口")
@Slf4j
@RestController
@RequestMapping("events")
@Validated
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @ApiModelProperty("创建申请")
    @PostMapping("/createEvents")
    public CommonResult createEvents(@RequestBody Events events){
        return eventsService.createEvents(events);
    }

    @ApiModelProperty("查询申请")
    @GetMapping("/getEvents")
    public CommonResult getEvents(@Param("eventId") String eventId){
        return eventsService.getEvents(eventId);
    }

    @ApiModelProperty("分页查询申请")
    @GetMapping("/getEventsPage")
    public CommonResult getEventsPage(EventsPageReq eventsPageReq){
        return eventsService.getEventsList(eventsPageReq);
    }

    @ApiModelProperty("分页查询完整申请（包括相关信息）")
    @GetMapping("/getEventsAllInfoPage")
    public CommonResult getEventsAllInfoPage(EventsPageReq eventsPageReq){
        return eventsService.getEventsAllInfoList(eventsPageReq);
    }

    @ApiModelProperty("更新申请")
    @PostMapping("/updateEvents")
    public CommonResult updateEvents(@RequestBody Events events){
        return eventsService.updateEvents(events);
    }

    @ApiModelProperty("辅导员审批")
    @GetMapping("/deptConfimEvents")
    public CommonResult deptConfimEvents(@Param("eventId") String eventId, @Param("pass") String pass){
        return eventsService.deptConfimEvents(eventId, pass);
    }

    @ApiModelProperty("学院审批")
    @GetMapping("/schoolConfimEvents")
    public CommonResult schoolConfimEvents(@Param("eventId") String eventId, @Param("pass") String pass){
        return eventsService.schoolConfimEvents(eventId, pass);
    }


    @ApiModelProperty("删除申请")
    @PostMapping("/deleteEvents")
    public CommonResult deleteEvents(@Param("eventId") String eventId){
        return eventsService.deleteEvents(eventId);
    }

//    @GetMapping("/test")
//    public CommonResult test(){
//        return CommonResult.success(new Events().setSchoolUpdatedDatetime(LocalDateTime.now()));
//    }
}
