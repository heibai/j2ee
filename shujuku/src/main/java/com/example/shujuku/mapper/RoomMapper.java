package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.RoomPageReq;
import com.example.shujuku.room.bean.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomMapper extends BaseMapper<Room>{

//    @Select("SELECT * FROM room WHERE room_Id = #{roomId}")
//    Room GetRoomByRoomId(String roomId);

    @Select("SELECT * FROM room WHERE room_Id = #{roomId} and status = 1")
    Room CheckRoom(String roomId);

    @Select("select * building_id from room where status != 0 order by building_id")
    List<Room> getRoomsGroupByBuildingId();

    List<Room> getResidentableRooms();

    List<Room> getResidentedRooms();

    List<Room> getRoomList(RoomPageReq req);

    List<Room> getAllRooms();
}