package com.example.shujuku.school.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.SchoolPageReq;
import com.example.shujuku.school.bean.School;

public interface SchoolService extends IService<School> {

    public CommonResult createSchool(School school);

    public CommonResult getSchoolBySchoolId(String schoolId);

    public CommonResult getSchoolBySchoolName(String schoolName);

    public CommonResult getSchoolPage(SchoolPageReq school);

    public CommonResult getSchoolList(SchoolPageReq req);

    public CommonResult updateSchool(School school);

    public CommonResult deleteSchool(String schoolId);
}
