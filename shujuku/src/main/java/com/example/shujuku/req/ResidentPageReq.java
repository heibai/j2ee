package com.example.shujuku.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.shujuku.common.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("resident")
public class ResidentPageReq extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

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