package com.example.shujuku.school.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.example.shujuku.common.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

//    学院id
    @TableId(value = "school_id",type = IdType.ID_WORKER_STR)
    private String schoolId;

//   学院名
    @TableField("school_name")
    private String schoolName;

//   学院所处校区id
    @TableField("school_location_id")
    private String schoolLocationId;

//   学院所处校区
    @TableField("school_location")
    private String schoolLocation;

}
