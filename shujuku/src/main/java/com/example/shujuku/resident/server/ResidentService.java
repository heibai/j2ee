package com.example.shujuku.resident.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.ResidentPageReq;
import com.example.shujuku.resident.bean.Resident;

public interface ResidentService extends IService<Resident> {

    public CommonResult createResident(Resident resident);

    public CommonResult getResidentByResidentId(String residentId);

    public CommonResult getResidentByUserId(String userId);

    public CommonResult getResidentList(ResidentPageReq req);

    public CommonResult getResidentDetailList(ResidentPageReq req);

    public CommonResult updateResident(Resident resident);

    public CommonResult deleteResident(String residentId);

}
