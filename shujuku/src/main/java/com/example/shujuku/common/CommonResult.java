package com.example.shujuku.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    //状态码 200成功
    private Integer code;

    //请求任务编号
    private String no;

    //响应信息
    private String msg;

    private T data;

    public CommonResult() {
    }

    public static CommonResult success(Object data){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SUCCESS.getResultCode();
        dto.msg = CommonResultEnum.SUCCESS.getResultMsg();
        dto.data = data;
        return dto;
    }
    public static CommonResult success(String msg){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SUCCESS.getResultCode();;
        dto.msg = msg;
        dto.data = null;
        return dto;
    }

    public static CommonResult success(String msg,Object data){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SUCCESS.getResultCode();
        dto.msg = msg;
        dto.data = data;
        return dto;
    }

    public static CommonResult success(int code ,String msg,Object data){
        CommonResult dto = new CommonResult();
        dto.code = code;
        dto.msg = msg;
        dto.data = data;
        return dto;
    }
    public static CommonResult fail(String msg){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SERVE_EXECPTION.getResultCode();
        dto.msg = msg;
        dto.data = null;
        return dto;
    }

    public static CommonResult fail(Object data){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SERVE_EXECPTION.getResultCode();
        dto.msg = CommonResultEnum.SERVE_EXECPTION.getResultMsg();
        dto.data = data;
        return dto;
    }


    public static CommonResult fail(String msg,Object data){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SERVE_EXECPTION.getResultCode();
        dto.msg = msg;
        dto.data = data;
        return dto;
    }

    public static CommonResult fail(int code ,String msg,Object data){
        CommonResult dto = new CommonResult();
        dto.code = code;
        dto.msg = msg;
        dto.data = data;
        return dto;
    }


    public static CommonResult ok(Object data,String no){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SUCCESS.getResultCode();
        dto.msg = CommonResultEnum.SUCCESS.getResultMsg();
        dto.data = data;
        dto.no = no;
        return dto;
    }
    public static CommonResult error(String msg,String no){
        CommonResult dto = new CommonResult();
        dto.code = CommonResultEnum.SERVE_EXECPTION.getResultCode();
        dto.msg = msg;
        dto.data = null;
        dto.no = no;
        return dto;
    }
    public T getData() {
        return data;
    }

}
