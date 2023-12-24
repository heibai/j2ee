package com.example.shujuku.resident.server.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        //检查房间是否已住满，返回不为null说明该房间还有空位
        Room room = roomMapper.CheckRoom(resident.getRoomId());
        if(room != null){
            //让房间已住人数+1
            int hadNum = Integer.parseInt(room.getHadNum())+1;
            int limitNum = Integer.parseInt(room.getLimitNum());
            if(hadNum == limitNum){
                room.setStatus(String.valueOf(2));
                room.setHadNum(room.getLimitNum());
            }else if(hadNum > limitNum){
                return CommonResult.fail("该房间已住满");
            }
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
        //分页查询住宿信息
        List<Resident> residentList = residentMapper.getResidentList(req);
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
        //获取所有住宿信息
        List<Resident> residentList = residentMapper.getResidentList(req);
        ListIterator<Resident> residentListIterator = residentList.listIterator();
        while(residentListIterator.hasNext()){
            List<Object> result = new ArrayList<Object>();
            Resident resident = residentListIterator.next();
            //根据住宿信息查询住户的个人信息
            Users users = usersMapper.GetUsersByUserId(resident.getUserId());
            //把2个对象装在list里然后把这个list装在resultList里
            result.add(resident);
            result.add(users);
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
            return CommonResult.success(resident);
        }else return CommonResult.fail("删除resident表数据失败");
    }
}