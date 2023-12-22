package com.example.shujuku.property.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.PropertyPageReq;
import com.example.shujuku.property.bean.Property;

public interface PropertyService extends IService<Property> {

    public CommonResult createProperty(Property property);

    public CommonResult getPropertyByPropertyId(String propertyId);

    public CommonResult getPropertyList(PropertyPageReq req);

    public CommonResult updateProperty(Property property);

    public CommonResult deleteProperty(String propertyId);

}
