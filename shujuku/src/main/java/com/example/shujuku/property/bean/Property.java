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

    @TableField("owner_level")
    private String ownerLevel;

    @TableField("owner_id")
    private String ownerId;

    @TableField("status")
    private String status;


}
