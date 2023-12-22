package com.example.shujuku.room.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.RoomPageReq;
import com.example.shujuku.room.bean.Room;

public interface RoomService extends IService<Room> {

    public CommonResult createRoom(Room room);

    public CommonResult getRoomById(String id);

    public  CommonResult getRoomByRoomIdAndBuildingId(String roomId, String buildingId);

    public CommonResult getResidentableRoom();

    public CommonResult getRoomsTotalDetail();

    public CommonResult getRoomUsers(String roomId);

    public CommonResult getRoomList(RoomPageReq req);

    public CommonResult updateRoom(Room room);

    public CommonResult deleteRoom(String id);




}
