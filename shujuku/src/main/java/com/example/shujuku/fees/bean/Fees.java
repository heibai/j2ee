package com.example.shujuku.fees.bean;

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
@TableName("fees")
public class Fees implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
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
