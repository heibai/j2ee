package com.example.shujuku.resident.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.shujuku.room.bean.Room;
import com.example.shujuku.users.bean.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resident")
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("room_id")
    private String roomId;

    @TableField("building_id")
    private String buildingId;

    @TableField("resident_begin_time")
    private LocalDateTime residentBeginTime;

    @TableField("resident_end_time")
    private LocalDateTime residentEndTime;

    //为0表示不可用，1表示入住中，2表示未入住
    @TableField("status")
    private String status;



}
