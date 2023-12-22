package com.example.shujuku.complaint.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.ComplaintPageReq;
import com.example.shujuku.complaint.bean.Complaint;

public interface ComplaintService extends IService<Complaint> {

    public CommonResult createComplaint(Complaint complaint);

    public CommonResult getComplaintById(String id);

    public CommonResult getComplaintPage(ComplaintPageReq complaintPageReq);

    public CommonResult getComplaintList(ComplaintPageReq req);

    public CommonResult updateComplaint(Complaint complaint);

    public CommonResult deleteComplaint(String complaintId);

}
