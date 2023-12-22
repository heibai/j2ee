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

    @Select("SELECT * FROM room WHERE id = #{id} ")
    Room GetRoomById(String id);

    @Select("SELECT * FROM room WHERE id = #{id} and status = 1")
    Room CheckRoom(String id);




    @Select("SELECT * FROM room WHERE room_Id = #{roomId} and building_id = #{buildingId}")
    Room getRoomByRoomIdAndBuildingId(String roomId,String buildingId);

    List<Room> getResidentableRooms();

    List<Room> getResidentedRooms();

    List<Room> getRoomList(RoomPageReq req);

    List<Room> getAllRooms();


}