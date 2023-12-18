package com.example.shujuku.stupass.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.stupass.bean.Stupass;

public interface StupassService extends IService<Stupass>{

    public CommonResult createStupass(Stupass stupass);

    public CommonResult getStupassByStupassId(String stupassId);

    public CommonResult updateStupass(Stupass stupass);

    public CommonResult deleteStupass(String stupassId);

    public CommonResult getStupassStatus(String studentId);

}

