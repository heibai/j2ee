package com.example.shujuku.common;

import lombok.Data;

@Data
public class Page {

    private Integer pageNo;

    private Integer pageSize;

    public Integer getPageNo() {
        if(null == pageNo){
            pageNo = 1;
        }
        return pageNo;
    }

    public Integer getPageSize() {
        if(null == pageSize){
            pageSize = 25;
        }
        return pageSize;
    }
}
