package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.events.bean.Events;
import com.example.shujuku.req.EventsPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventsMapper extends BaseMapper<Events> {

    List<Events> getEventsList(EventsPageReq req);

    List<Events> getEventsAllInfoList(EventsPageReq req);

}
