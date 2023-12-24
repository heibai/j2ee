package com.example.shujuku.property.server.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shujuku.common.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shujuku.req.PropertyPageReq;
import com.example.shujuku.property.bean.Property;
import com.example.shujuku.mapper.PropertyMapper;
import com.example.shujuku.property.server.PropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {

    @Autowired
    protected PropertyMapper propertyMapper;

    @Override
    public CommonResult createProperty(Property property){
        boolean insertSuccess = SqlHelper.retBool(propertyMapper.insert(property));
        if(!insertSuccess){
            log.info("插入property表失败",property);
            return CommonResult.fail("插入property表失败");
        }
        return CommonResult.success(property);
    }

    @Override
    public CommonResult getPropertyById(String id){
        Property property = propertyMapper.selectById(id);
        if(property != null){
            return CommonResult.success(property);
        }else return CommonResult.fail("查询property表失败");
    }

    @Override
    public CommonResult getPropertyList(PropertyPageReq req){
        Integer pageNo = req.getPageNo();
        Integer pageSize = req.getPageSize();
        req.setPageNo((pageNo - 1)*pageSize);
        System.out.println(req);
        //分页查询公共财产记录
        List<Property> propertyList = propertyMapper.getPropertyList(req);
        Page<Property> page = new Page<>(pageNo, pageSize);
        page.setRecords(propertyList);
        page.setTotal(propertyList.size());
        return CommonResult.success(page);
    }

    @Override
    public CommonResult updateProperty(Property property){
        Property oldProperty = propertyMapper.selectById(property.getId());
        Assert.notNull(oldProperty, "修改property表失败，表中查询不到对应propertyId的教师");
        if(SqlHelper.retBool(baseMapper.updateById(property))){
            return CommonResult.success(property);
        }else return CommonResult.fail("更新property表失败");
    }

    @Override
    public CommonResult deleteProperty(String id){
        Property property = propertyMapper.selectById(id);
        Assert.notNull(property, "删除property表数据失败，表中查询不到对应propertyId的申请");
        if(SqlHelper.retBool(baseMapper.deleteById(id))){
            return CommonResult.success(property);
        }else return CommonResult.fail("删除property表数据失败");
    }
}