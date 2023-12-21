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
@TableName("room")
public class RoomPageReq extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("room_id")
    private String roomId;

    @TableField("building_id")
    private String buildingId;

    //可入住人数
    @TableField("limit_num")
    private String limitNum;

    //已入住人数
    @TableField("had_num")
    private String hadNum;

    //为0表示不可用，1表示入住中，2表示未入住
    @TableField("status")
    private String status;

}