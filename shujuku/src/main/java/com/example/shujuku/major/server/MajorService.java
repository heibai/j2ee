package com.example.shujuku.major.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.major.bean.Major;
import com.example.shujuku.req.MajorPageReq;
import com.example.shujuku.req.StudentPageReq;
import com.example.shujuku.student.bean.Student;

public interface MajorService extends IService<Major> {

    public CommonResult createMajor(Major major);

    public CommonResult getMajor(String majorId);

    public CommonResult getMajorPage(MajorPageReq majorPageReq);

    public CommonResult getMajorList(MajorPageReq req);

    public CommonResult updateMajor(Major major);

    public CommonResult deleteMajor(String majorId);
}
