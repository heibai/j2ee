package com.example.shujuku.room.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.RoomPageReq;
import com.example.shujuku.room.bean.Room;
import com.example.shujuku.room.server.RoomService;
import com.example.shujuku.room.bean.Room;
import com.example.shujuku.room.server.RoomService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("房间信息相关接口")
@Slf4j
@RestController
@RequestMapping("room")
@Validated
public class RoomController {

    @Autowired
    private RoomService roomService;

    @ApiModelProperty("创建房间信息")
    @PostMapping("/createRoom")
    public CommonResult createRoom(@RequestBody Room room){
        return roomService.createRoom(room);
    }

    @ApiModelProperty("查询可入住房间信息")
    @GetMapping("/getResidentableRoom")
    public CommonResult getResidentableRoom(){
        return roomService.getResidentableRoom();
    }

    @ApiModelProperty("查询住房整体信息")
    @GetMapping("/getRoomsTotalDetail")
    public CommonResult getRoomsTotalDetail(){
        return roomService.getRoomsTotalDetail();
    }

    @ApiModelProperty("查询房间住客")
    @GetMapping("/getRoomUsers")
    public CommonResult getRoomUsers(@Param("roomId") String roomId){
        return roomService.getRoomUsers(roomId);
    }

    @ApiModelProperty("查询房间信息")
    @GetMapping("/getRoom")
    public CommonResult getRoom(@Param("id") String id){
        return roomService.getRoomById(id);
    }

    @ApiModelProperty("分页查询房间信息")
    @GetMapping("/getRoomPage")
    public CommonResult getRoomPage(RoomPageReq roomPageReq){
        return roomService.getRoomList(roomPageReq);
    }

    @ApiModelProperty("更新房间信息")
    @PostMapping("/updateRoom")
    public CommonResult updateRoom(@RequestBody Room room){
        return roomService.updateRoom(room);
    }

    @ApiModelProperty("删除房间信息")
    @GetMapping("/deleteRoom")
    public CommonResult deleteRoom(@Param("id") String id){
        return roomService.deleteRoom(id);
    }
}
