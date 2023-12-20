package com.example.shujuku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shujuku.req.PropertyPageReq;
import com.example.shujuku.property.bean.Property;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PropertyMapper extends BaseMapper<Property>{

    @Select("SELECT * FROM property WHERE propertyId = #{propertyId}")
    Property GetPropertyByPropertyId(String propertyId);

    List<Property> getPropertyList(PropertyPageReq req);

}