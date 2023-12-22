package com.example.shujuku.fees.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fees")
public class Fees implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("fees_id")
    private String feesId;

    //为0表示房费，为1表示水电费
    @TableField("type")
    private String type;

    @TableField("price")
    private Double price;

    @TableField("room_id")
    private String roomId;

    @TableField("message")
    private String message;

    //为0表示已删除，为1表示待缴费，为2表示已缴费，为3表示已过期
    @TableField("status")
    private String status;

    @TableField("time")
    private LocalDateTime time;

    @TableField("deadline")
    private LocalDateTime deadline;

}
