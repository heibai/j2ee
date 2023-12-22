package com.example.shujuku.fees.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.FeesPageReq;
import com.example.shujuku.fees.bean.Fees;
import com.example.shujuku.fees.server.FeesService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("费用信息相关接口")
@Slf4j
@RestController
@RequestMapping("fees")
@Validated
public class FeesController {

    @Autowired
    private FeesService feesService;

    @ApiModelProperty("一键生成住宿费")
    @GetMapping("/createResidentFees")
    public CommonResult createResidentFees(@Param("price") String price,@Param("deadline") String deadline, @Param("type")String type){
        return feesService.createResidentFees(price,deadline,type);
    }

    @ApiModelProperty("创建费用信息")
    @PostMapping("/createFees")
    public CommonResult createFees(@RequestBody Fees fees){
        return feesService.createFees(fees);
    }

    @ApiModelProperty("查询费用信息")
    @GetMapping("/getFees")
    public CommonResult getFees(@Param("feesId") String feesId){
        return feesService.getFeesByFeesId(feesId);
    }

    @ApiModelProperty("分页查询费用信息")
    @GetMapping("/getFeesPage")
    public CommonResult getFeesPage(FeesPageReq feesPageReq){
        return feesService.getFeesList(feesPageReq);
    }

    @ApiModelProperty("更新费用信息")
    @PostMapping("/updateFees")
    public CommonResult updateFees(@RequestBody Fees fees){
        return feesService.updateFees(fees);
    }

    @ApiModelProperty("删除费用信息")
    @GetMapping("/deleteFees")
    public CommonResult deleteFees(@Param("feesId") String feesId){
        return feesService.deleteFees(feesId);
    }
}
