package com.example.shujuku.property.controller;

import com.example.shujuku.common.CommonResult;
import com.example.shujuku.req.PropertyPageReq;
import com.example.shujuku.property.bean.Property;
import com.example.shujuku.property.server.PropertyService;
import com.example.shujuku.property.bean.Property;
import com.example.shujuku.property.server.PropertyService;
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
@RequestMapping("property")
@Validated
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @ApiModelProperty("创建入住信息")
    @PostMapping("/createProperty")
    public CommonResult createProperty(@RequestBody Property property){
        return propertyService.createProperty(property);
    }

    @ApiModelProperty("查询入住信息")
    @GetMapping("/getProperty")
    public CommonResult getProperty(@Param("id") String id){
        return propertyService.getPropertyById(id);
    }

    @ApiModelProperty("分页查询入住信息")
    @GetMapping("/getPropertyPage")
    public CommonResult getPropertyPage(PropertyPageReq propertyPageReq){
        return propertyService.getPropertyList(propertyPageReq);
    }

    @ApiModelProperty("更新入住信息")
    @PostMapping("/updateProperty")
    public CommonResult updateProperty(@RequestBody Property property){
        return propertyService.updateProperty(property);
    }

    @ApiModelProperty("删除入住信息")
    @GetMapping("/deleteProperty")
    public CommonResult deleteProperty(@Param("id") String id){
        return propertyService.deleteProperty(id);
    }
}
