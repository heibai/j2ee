package com.example.shujuku.major.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("major")
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

//    专业id
    @TableId(value = "major_id", type = IdType.ID_WORKER_STR)
    private String majorId;

//    专业名称
    @TableField("major_name")
    private String majorName;

//  系id
    @TableField("dept_id")
    private String deptId;

//  系名
    @TableField("dept_name")
    private String deptName;

//   学院id
    @TableField("school_id")
    private String schoolId;

//   学院名
    @TableField("school_name")
    private String schoolName;

//   辅导员id
    @TableField("teacher_id")
    private String teacherId;

//   辅导员姓名
    @TableField("teacher_name")
    private String teacherName;

}
