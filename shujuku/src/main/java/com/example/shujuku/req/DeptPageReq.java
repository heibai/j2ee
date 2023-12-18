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
@TableName("dept")
public class DeptPageReq extends Page implements Serializable {
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

