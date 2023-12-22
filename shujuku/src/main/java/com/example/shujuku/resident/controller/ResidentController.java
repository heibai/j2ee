package com.example.shujuku.resident.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.ResidentPageReq;
import com.example.shujuku.resident.bean.Resident;
import com.example.shujuku.resident.server.ResidentService;
import com.example.shujuku.resident.bean.Resident;
import com.example.shujuku.resident.server.ResidentService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("入住信息相关接口")
@Slf4j
@RestController
@RequestMapping("resident")
@Validated
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @ApiModelProperty("创建入住信息")
    @PostMapping("/createResident")
    public CommonResult createResident(@RequestBody Resident resident){
        return residentService.createResident(resident);
    }

    @ApiModelProperty("查询入住信息")
    @GetMapping("/getResident")
    public CommonResult getResident(@Param("residentId") String residentId){
        return residentService.getResidentByResidentId(residentId);
    }

    @ApiModelProperty("分页查询入住信息")
    @GetMapping("/getResidentPage")
    public CommonResult getResidentPage(ResidentPageReq residentPageReq){
        return residentService.getResidentList(residentPageReq);
    }

    @ApiModelProperty("分页联查入住信息")
    @GetMapping("/getResidentDetailPage")
    public CommonResult getResidentDetailPage(ResidentPageReq residentPageReq){
        return residentService.getResidentDetailList(residentPageReq);
    }

    @ApiModelProperty("更新入住信息")
    @PostMapping("/updateResident")
    public CommonResult updateResident(@RequestBody Resident resident){
        return residentService.updateResident(resident);
    }

    @ApiModelProperty("删除入住信息")
    @GetMapping("/deleteResident")
    public CommonResult deleteResident(@Param("residentId") String residentId){
        return residentService.deleteResident(residentId);
    }
}
