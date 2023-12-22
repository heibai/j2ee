package com.example.shujuku.repair.report.bean;

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
@TableName("repair_report")
public class RepairReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("report_id")
    private String reportId;

    @TableField("reporter_id")
    private String reporterId;

    @TableField("message")
    private String message;

    //0表示已删除，1表示待受理，2表示已受理，3表示已处理，4表示已拒绝受理
    @TableField("status")
    private String status;

    @TableField("report_time")
    private LocalDateTime reportTime;

    @TableField("repair_time")
    private LocalDateTime repairTime;

    @TableField("repairer_id")
    private String repairerId;

    @TableField("repairer_message")
    private String repairerMessage;
}
