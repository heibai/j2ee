package com.example.shujuku.fees.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.FeesPageReq;
import com.example.shujuku.fees.bean.Fees;

public interface FeesService extends IService<Fees> {

    public CommonResult createResidentFees(String price, String deadline,String type);

    public CommonResult createFees(Fees fees);

    public CommonResult getFeesById(String id);

    public CommonResult getFeesByRoomId(String roomId);

    public CommonResult getFeesList(FeesPageReq req);

    public CommonResult updateFees(Fees fees);

    public CommonResult deleteFees(String id);

}
