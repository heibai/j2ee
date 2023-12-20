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
@TableName("fees")
public class FeesPageReq extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    //   教师工号
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField("fees_id")
    private String feesId;

    @TableField("type")
    private String type;

    @TableField("price")
    private Double price;

    @TableField("room_id")
    private String roomId;

    @TableField("message")
    private String message;

    @TableField("status")
    private String status;

    @TableField("deadline")
    private DateTime deadline;

}