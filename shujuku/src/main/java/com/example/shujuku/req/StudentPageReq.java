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

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("student")
public class StudentPageReq extends Page implements Serializable {

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
