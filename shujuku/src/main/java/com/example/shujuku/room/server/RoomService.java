package com.example.shujuku.room.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.RoomPageReq;
import com.example.shujuku.room.bean.Room;

public interface RoomService extends IService<Room> {

    public CommonResult createRoom(Room room);

    public CommonResult getRoomByRoomId(String roomId);

    public CommonResult getResidentableRoom();

    public CommonResult getRoomPage(RoomPageReq roomPageReq);

    public CommonResult getRoomList(RoomPageReq req);

    public CommonResult updateRoom(Room room);

    public CommonResult deleteRoom(String roomId);

}
