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
@TableName("teacher")
public class TeacherPageReq extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    //   教师工号
    @TableId(value = "teacher_id", type = IdType.INPUT)
    private String teacherId;

    //    教师姓名
    @TableField("teacher_name")
    private String teacherName;

    //   联系电话
    @TableField("phone")
    private String phone;

    //    办公地点
    @TableField("work_place")
    private String workPlace;

    //    办公时间
    @TableField("work_day")
    private String workDay;

    //    系表id
    @TableField("dept_id")
    private String deptId;

}