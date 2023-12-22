package com.example.shujuku.room.server.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.mapper.ResidentMapper;
import com.example.shujuku.mapper.UsersMapper;
import com.example.shujuku.req.RoomPageReq;
import com.example.shujuku.resident.bean.Resident;
import com.example.shujuku.room.bean.Room;
import com.example.shujuku.mapper.RoomMapper;
import com.example.shujuku.room.server.RoomService;
import com.example.shujuku.users.bean.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Autowired
    protected RoomMapper roomMapper;
    @Autowired
    protected ResidentMapper residentMapper;
    @Autowired
    protected UsersMapper usersMapper;

    @Override
    public CommonResult createRoom(Room room) {
        if (room.getLimitNum() != null) {
            if (room.getHadNum() == null) {
                room.setHadNum("0");
            }
            boolean insertSuccess = SqlHelper.retBool(roomMapper.insert(room));
            if (!insertSuccess) {
                log.info("插入room表失败", room);
                return CommonResult.fail("插入room表失败");
            }
            return CommonResult.success(room);
        }
        return CommonResult.fail("请输入可住人数");
    }

    @Override
    public CommonResult getResidentableRoom() {
        List<Room> roomList = roomMapper.getResidentableRooms();
        return CommonResult.success(roomList);
    }

    @Override
    public CommonResult getRoomsTotalDetail() {
        List<Room> roomList = roomMapper.getAllRooms();
        HashMap<String, Object> resultmap = new HashMap<String, Object>();
        HashMap<String, HashMap<String, Integer>> buildingHashMap = new HashMap<String, HashMap<String, Integer>>();
        int buildingNum = 0;
        int totalHadNum = 0;
        int totalLimitNum = 0;
        int totalRoomNum = 0;
        ListIterator<Room> roomListIterator = roomList.listIterator();
        while (roomListIterator.hasNext()) {
            Room room = roomListIterator.next();
            System.out.println(buildingHashMap.get(room.getBuildingId()));
            if (buildingHashMap.get(room.getBuildingId()) == null) {
                HashMap<String, Integer> buildingDetailHashMap = new HashMap<String, Integer>();
                buildingDetailHashMap.put("roomNum", 1);
                buildingDetailHashMap.put("hadNum", Integer.valueOf(room.getHadNum()));
                buildingDetailHashMap.put("limitNum", Integer.valueOf(room.getLimitNum()));
                buildingHashMap.put(room.getBuildingId(), buildingDetailHashMap);
                buildingNum = buildingNum + 1;
            } else {
                HashMap<String, Integer> buildingDetailHashMap = buildingHashMap.get(room.getBuildingId());
                buildingDetailHashMap.put("roomNum", buildingDetailHashMap.get("roomNum") + 1);
                buildingDetailHashMap.put("hadNum", buildingDetailHashMap.get("hadNum") + Integer.valueOf(room.getHadNum()));
                buildingDetailHashMap.put("limitNum", buildingDetailHashMap.get("limitNum") + Integer.valueOf(room.getLimitNum()));
                buildingHashMap.put(room.getBuildingId(), buildingDetailHashMap);
            }
            totalRoomNum = totalRoomNum + 1;
            totalHadNum = totalHadNum + Integer.valueOf(room.getHadNum());
            totalLimitNum = totalLimitNum + Integer.valueOf(room.getLimitNum());
        }
        resultmap.put("buildingNum", buildingNum);
        resultmap.put("totalRoomNum", totalRoomNum);
        resultmap.put("totalHadNum", totalHadNum);
        resultmap.put("totalLimitNum", totalLimitNum);
        resultmap.put("buildingHashMap", buildingHashMap);
        return CommonResult.success(resultmap);
    }

    @Override
    public CommonResult getRoomById(String roomId) {
        Room room = roomMapper.GetRoomById(roomId);
        if (room != null) {
            return CommonResult.success(room);
        } else return CommonResult.fail("查询room表失败");
    }

    @Override
    public  CommonResult getRoomByRoomIdAndBuildingId(String roomId, String buildingId) {
        Room room = roomMapper.getRoomByRoomIdAndBuildingId(roomId, buildingId);
        if (room != null) {
            return CommonResult.success(room);
        } else return CommonResult.fail("查询room表失败");
    }

    @Override
    public CommonResult getRoomUsers(String roomId) {
        List<Resident> residentList = residentMapper.getResidentListByRoomId(roomId);
        List<Users> usersList = new ArrayList<Users>();
        ListIterator<Resident> residentListIterator = residentList.listIterator();
        while (residentListIterator.hasNext()) {
            Resident resident = residentListIterator.next();
            Users users = usersMapper.GetUsersById(resident.getUserId());
            usersList.add(users);
        }
        return CommonResult.success(usersList);
    }

    @Override
    public CommonResult getRoomList(RoomPageReq req) {
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1) * pageSize);
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
    public CommonResult updateRoom(Room room) {
        Room oldRoom = roomMapper.selectById(room.getId());

        Assert.notNull(oldRoom, "修改room表失败，表中查询不到对应roomId的教师");
        if (SqlHelper.retBool(baseMapper.updateById(room))) {
            return CommonResult.success(room);
        } else return CommonResult.fail("更新room表失败");
    }

    @Override
    public CommonResult deleteRoom(String roomId) {
        Room room = roomMapper.GetRoomById(roomId);
        Assert.notNull(room, "删除room表数据失败，表中查询不到对应roomId的申请");
        if (SqlHelper.retBool(baseMapper.deleteById(roomId))) {
            return CommonResult.success(room);
        } else return CommonResult.fail("删除room表数据失败");
    }



}