package com.example.shujuku.events.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("events")
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;
    /*
    事件ID
     */
    @TableId(value = "event_id", type = IdType.ID_WORKER_STR)
    private String eventId;

    /*
    学号
     */
    @TableField("student_id")
    private String studentId;

    /*
    离校时间
     */
    @TableField("event_begins")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventBegins;

    /*
    返校时间
     */
    @TableField("event_ends")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventEnds;

    /*
    离校范围
     */
    @TableField("event_area")
    private String eventArea;

    /*
    目前位置（校内/校外）
     */
    @TableField("location")
    private String location;

    /*
    具体去处
     */
    @TableField("event_location")
    private String eventLocation;

    /*
    申请理由
     */
    @TableField("event_note")
    private String eventNote;

    /*
    申请状态,  eventStatus，0为已删除，1为待审批，2为辅导员已审批（审批完成），3为辅导员已审批（待学院审批），4为学院已审批，5为辅导员已拒绝，6为学院已拒绝
     */
    @TableField("event_status")
    private String eventStatus;

    /*
    专业Id
     */
    @TableField("major_id")
    private String majorId;

    /*
    系Id
     */
    @TableField("dept_id")
    private String deptId;

    /*
    学院Id
     */
    @TableField("school_id")
    private String schoolId;

    /*
    创建时间
     */
    @TableField(value = "created_datetime", fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDatetime;

    /*
    辅导员审批时间
     */
    @TableField("dept_updated_datetime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deptUpdatedDatetime;

    /*
    学院审批时间
     */
    @TableField("school_updated_datetime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime schoolUpdatedDatetime;

}
