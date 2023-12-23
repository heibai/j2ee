package com.example.shujuku.resident.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.common.Tool;
import com.example.shujuku.mapper.RoomMapper;
import com.example.shujuku.mapper.UsersMapper;
import com.example.shujuku.req.ResidentPageReq;
import com.example.shujuku.resident.bean.Resident;
import com.example.shujuku.mapper.ResidentMapper;
import com.example.shujuku.resident.server.ResidentService;
import com.example.shujuku.room.bean.Room;
import com.example.shujuku.users.bean.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Service
public class ResidentServiceImpl extends ServiceImpl<ResidentMapper, Resident> implements ResidentService {

    @Autowired
    protected ResidentMapper residentMapper;
    @Autowired
    protected RoomMapper roomMapper;
    @Autowired
    protected UsersMapper usersMapper;

    @Override
    public CommonResult createResident(Resident resident){
        Room room = roomMapper.CheckRoom(resident.getRoomId());
        if(room != null){
            int hadNum = Integer.parseInt(room.getHadNum())+1;
            int limitNum = Integer.parseInt(room.getLimitNum());
            room.setStatus(String.valueOf(1));
            if(hadNum == limitNum){
                room.setStatus(String.valueOf(2));
                room.setHadNum(room.getLimitNum());
            }else if(hadNum > limitNum){
                return CommonResult.fail("该房间已住满");
            }
            room.setHadNum(String.valueOf(hadNum));
            boolean insertSuccess = SqlHelper.retBool(residentMapper.insert(resident));
            boolean insertSuccess2 = SqlHelper.retBool(roomMapper.updateById(room));
            if(!insertSuccess || !insertSuccess2){
                log.info("创建入住信息失败",resident);
                return CommonResult.fail("创建入住信息失败");
            }
            return CommonResult.success(resident);
        }
        return CommonResult.fail("找不到该房间");
    }


    @Override
    public  CommonResult getResidentByUserId(String userId){
        Resident resident = residentMapper.GetResidentByUserId(userId);
        if(resident != null){
            return CommonResult.success(resident);
        }else return CommonResult.fail("查询resident表失败");
    }

    @Override
    public CommonResult getResidentById(String id){
        Resident resident = residentMapper.selectById(id);
        if(resident != null){
            return CommonResult.success(resident);
        }else return CommonResult.fail("查询resident表失败");
    }

    @Override
    public CommonResult getResidentList(ResidentPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        List<List<Object>> resultList = new ArrayList<List<Object>>();
        List<Resident> residentList = residentMapper.getResidentList(req);
//       联查出用户信息 住户信息 和 住房信息
        ListIterator<Resident> residentListIterator = residentList.listIterator();
        while(residentListIterator.hasNext()){

            //Users user = usersMapper.GetUsersById(resident.getUserId());
            //Room room = roomMapper.selectById(resident.getRoomId());
            //resident.setUser(user);
            //resident.setRoom(room);
        }
        Page<Resident> page = new Page<>(pageNo, pageSize);
        page.setRecords(residentList);
        page.setTotal(residentList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult getResidentDetailList(ResidentPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        List<List<Object>> resultList = new ArrayList<List<Object>>();
        List<Resident> residentList = residentMapper.getResidentList(req);
        ListIterator<Resident> residentListIterator = residentList.listIterator();
        while(residentListIterator.hasNext()){
            List<Object> result = new ArrayList<Object>();
            Resident resident = residentListIterator.next();
            Users users = usersMapper.GetUsersById(resident.getUserId());
            Room room = roomMapper.selectById(resident.getRoomId());
            result.add(resident);
            result.add(users);
            result.add(room);
            resultList.add(result);
        }

        Page<List<Object>> page = new Page<>(pageNo, pageSize);
        page.setRecords(resultList);
        page.setTotal(residentList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateResident(Resident resident){
        Resident oldResident = residentMapper.selectById(resident.getUserId());
        Assert.notNull(oldResident, "修改resident表失败，表中查询不到对应residentId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(resident))){
            return CommonResult.success(resident);
        }else return CommonResult.fail("更新resident表失败");
    }

    @Override
    public CommonResult deleteResident(String id){
        Resident resident = residentMapper.selectById(id);
        Assert.notNull(resident, "删除resident表数据失败，表中查询不到对应residentId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(id))){
            //还需要将user表中的用户身份改为resident
            Users users = usersMapper.selectById(resident.getUserId());
            users.setRole("visitor");
            usersMapper.updateById(users);
            //更新room表中的住户数量
            Room room = roomMapper.selectById(resident.getRoomId());
            int hadNum = Integer.parseInt(room.getHadNum())-1;
            room.setHadNum(String.valueOf(hadNum));
            if(hadNum == 0){
                room.setStatus(String.valueOf(0));
            }else if(hadNum < Integer.parseInt(room.getLimitNum())){
                room.setStatus(String.valueOf(1));
            }
            roomMapper.updateById(room);
            return CommonResult.success(resident);
        }else return CommonResult.fail("删除resident表数据失败");
    }
}