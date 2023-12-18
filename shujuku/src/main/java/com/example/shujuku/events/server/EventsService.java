package com.example.shujuku.events.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.events.bean.Events;
import com.example.shujuku.req.EventsPageReq;

public interface EventsService extends IService<Events> {
    public CommonResult createEvents(Events events);

    public CommonResult getEvents(String eventId);

    public CommonResult getEventsPage(EventsPageReq eventsPageReq);

    public CommonResult getEventsList(EventsPageReq eventsPageReq);

    public CommonResult getEventsAllInfoList(EventsPageReq eventsPageReq);

    public CommonResult updateEvents(Events events);

    public CommonResult deptConfimEvents(String eventId, String pass);

    public CommonResult schoolConfimEvents(String eventId, String pass);

    public CommonResult deleteEvents(String eventsId);
}
