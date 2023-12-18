package com.example.shujuku.stupass.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("stupass")
public class Stupass implements Serializable {

    private static final long serialVersionUID = 1L;
    //    学号
    @TableId(value = "stupass_id", type = IdType.ID_WORKER_STR)
    private String stupassId;

    @TableField("student_id")
    private String studentId;

    //    返校码有效期的开始日期
    @TableField("stu_pass_begins")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stuPassBegins;

    //    返校码有效期的结束日期
    @TableField("stu_pass_ends")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stuPassEnds;


}
