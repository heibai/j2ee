package com.example.shujuku.room.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.common.Tool;
import com.example.shujuku.req.RoomPageReq;
import com.example.shujuku.room.bean.Room;
import com.example.shujuku.mapper.RoomMapper;
import com.example.shujuku.room.server.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    protected RoomMapper roomMapper;

    @Override
    public CommonResult createRoom(Room room){
        boolean insertSuccess = SqlHelper.retBool(roomMapper.insert(room));
        if(!insertSuccess){
            log.info("插入room表失败",room);
            return CommonResult.fail("插入room表失败");
        }
        return CommonResult.success(room);
    }

    public CommonResult getResidentableRoom(){
        List<Room> roomList = roomMapper.getResidentableRooms();
        return CommonResult.success(roomList);
    }

    @Override
    public CommonResult getRoomByRoomId(String roomId){
        System.out.println(roomId);
        Room room = roomMapper.selectById(roomId);
        if(room != null){
            return CommonResult.success(room);
        }else return CommonResult.fail("查询room表失败");
    }

    @Override
    public CommonResult getRoomPage(RoomPageReq room) {
        Page<Room> page = new Page<>(room.getPageNo(), room.getPageSize());
        LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<Room>();
        //多条件匹配查询
//        queryWrapper.like(Tool.isPresent(room.getName()), Room::getName, room.getName());
        queryWrapper.eq(Tool.isPresent(room.getRoomId()), Room::getRoomId, room.getRoomId());
        queryWrapper.eq(Tool.isPresent(room.getBuildingId()), Room::getBuildingId, room.getBuildingId());
        queryWrapper.eq(Tool.isPresent(room.getStatus()), Room::getStatus, room.getStatus());
//查询
        IPage<Room> ipage = this.baseMapper.selectPage(page, queryWrapper);
        return CommonResult.success(ipage);
    }

    public CommonResult getRoomList(RoomPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        List<Room> roomList = roomMapper.getRoomList(req);
//        List<Student> list = studentMapper.getStudentList(req);
//        List<Student> studentList = (List<Student>) list.get(0);
//        Integer total = ((List<Integer>) list.get(1)).get(0);
//        Integer pages = (total == 0) ? 1 : ((total % pageSize == 0) ? total / pageSize : total / pageSize + 1);
        Page<Room> page = new Page<>(pageNo, pageSize);
        page.setRecords(roomList);
        page.setTotal(roomList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateRoom(Room room){
        Room oldRoom = roomMapper.selectById(room.getRoomId());
        Assert.notNull(oldRoom, "修改room表失败，表中查询不到对应roomId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(room))){
            return CommonResult.success(room);
        }else return CommonResult.fail("更新room表失败");
    }

    @Override
    public CommonResult deleteRoom(String roomId){
        Room room = roomMapper.selectById(roomId);
        Assert.notNull(room, "删除room表数据失败，表中查询不到对应roomId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(roomId))){
            return CommonResult.success(room);
        }else return CommonResult.fail("删除room表数据失败");
    }
}