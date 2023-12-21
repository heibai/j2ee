package com.example.shujuku.complaint.bean;

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
@TableName("complaint")
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("complaint_id")
    private String complaintId;

    @TableField("message")
    private String message;

    //为0表示已删除，为1表示待处理，为2表示已处理
    @TableField("status")
    private String status;

    @TableField("responses")
    private String responses;

}
