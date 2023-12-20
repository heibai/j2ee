package com.example.shujuku.resident.bean;

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
@TableName("resident")
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;

    //   教师工号
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("room_id")
    private String roomId;

    @TableField("building_id")
    private String buildingId;

    @TableField("resident_begin_time")
    private DateTime residentBeginTime;

    @TableField("resident_end_time")
    private DateTime residentEndTime;

    @TableField("status")
    private String status;


}
