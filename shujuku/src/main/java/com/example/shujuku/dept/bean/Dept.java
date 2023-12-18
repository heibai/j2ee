package com.example.shujuku.dept.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    //  系id
    @TableId(value = "dept_id", type = IdType.ID_WORKER_STR)
    private String deptId;

    //  系名
    @TableField("dept_name")
    private String deptName;

    //学院id
    @TableField("school_id")
    private String schoolId;

}
