package com.example.shujuku.property.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("property")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("property_id")
    private String propertyId;

    @TableField("name")
    private String name;

    @TableField("message")
    private String message;

    //为0表示小区持有，1表示楼栋持有，2表示房间持有
    @TableField("owner_level")
    private String ownerLevel;

    //owner_level为0时，此处能为空，为1时此处为building_id，为2时此处为room_id
    @TableField("owner_id")
    private String ownerId;

    //为0表示已删除，为1表示正常
    @TableField("status")
    private String status;


}
