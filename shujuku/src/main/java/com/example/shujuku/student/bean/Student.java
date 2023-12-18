package com.example.shujuku.student.bean;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    学号
     */
    @TableId(value = "student_id",type = IdType.INPUT)
    private String studentId;

    /*
    姓名
     */
    @TableField("student_name")
    private String studentName;

    /*
    性别
     */
    @TableField("gender")
    private String gender;

    /*
    专业
     */
    @TableField("major_id")
    private String majorId;

    /*
    系
     */
    @TableField("dept_id")
    private String deptId;

    /*
    学院
     */
    @TableField("school_id")
    private String schoolId;

}
