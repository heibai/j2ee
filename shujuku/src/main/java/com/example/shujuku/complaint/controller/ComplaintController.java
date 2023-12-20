package com.example.shujuku.complaint.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.ComplaintPageReq;
import com.example.shujuku.complaint.bean.Complaint;
import com.example.shujuku.complaint.server.ComplaintService;
import com.example.shujuku.complaint.bean.Complaint;
import com.example.shujuku.complaint.server.ComplaintService;
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
@RequestMapping("complaint")
@Validated
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @ApiModelProperty("创建入住信息")
    @PostMapping("/createComplaint")
    public CommonResult createComplaint(@RequestBody Complaint complaint){
        return complaintService.createComplaint(complaint);
    }

    @ApiModelProperty("查询入住信息")
    @GetMapping("/getComplaint")
    public CommonResult getComplaint(@Param("complaintId") String complaintId){
        return complaintService.getComplaintByComplaintId(complaintId);
    }

    @ApiModelProperty("分页查询入住信息")
    @GetMapping("/getComplaintPage")
    public CommonResult getComplaintPage(ComplaintPageReq complaintPageReq){
        return complaintService.getComplaintList(complaintPageReq);
    }

    @ApiModelProperty("更新入住信息")
    @PostMapping("/updateComplaint")
    public CommonResult updateComplaint(@RequestBody Complaint complaint){
        return complaintService.updateComplaint(complaint);
    }

    @ApiModelProperty("删除入住信息")
    @GetMapping("/deleteComplaint")
    public CommonResult deleteComplaint(@Param("complaintId") String complaintId){
        return complaintService.deleteComplaint(complaintId);
    }
}
