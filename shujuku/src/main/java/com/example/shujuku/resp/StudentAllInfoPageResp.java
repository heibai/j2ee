package com.example.shujuku.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class StudentAllInfoPageResp implements Serializable {

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

    //    专业名称
    @TableField("major_name")
    private String majorName;

    //  系名
    @TableField("dept_name")
    private String deptName;

    //   学院名
    @TableField("school_name")
    private String schoolName;
}
