package com.example.shujuku.major.controller;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.major.bean.Major;
import com.example.shujuku.major.server.MajorService;
import com.example.shujuku.req.MajorPageReq;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("专业相关接口")
@Slf4j
@RestController
@RequestMapping("/major")
@Validated
public class MajorController {

    @Autowired
    private MajorService majorService;

    @ApiModelProperty("创建专业")
    @PostMapping("/createMajor")
    public CommonResult createMajor(@RequestBody Major major){
        return majorService.createMajor(major);
    }

    @ApiModelProperty("查询专业")
    @GetMapping("/getMajor")
    public CommonResult getMajor(@Param("majorId") String majorId){
        return majorService.getMajor(majorId);
    }

    @ApiModelProperty("分页查询专业")
    @GetMapping("/getMajorPage")
    public CommonResult getMajorPage(MajorPageReq majorPageReq){
        return majorService.getMajorList(majorPageReq);
    }

    @ApiModelProperty("更新专业")
    @PostMapping("/updateMajor")
    public CommonResult updateMajor(@RequestBody Major major){
        return majorService.updateMajor(major);
    }

    @ApiModelProperty("删除专业")
    @GetMapping("/deleteMajor")
    public CommonResult deleteMajor(@Param("majorId") String majorId){
        return majorService.deleteMajor(majorId);
    }

}
