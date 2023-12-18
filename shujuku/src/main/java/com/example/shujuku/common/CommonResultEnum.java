package com.example.shujuku.common;

public enum CommonResultEnum implements BaseErrorInfoInterface {
    SUCCESS(200,"success"),
    SERVE_EXECPTION(10001,"请求服务异常"),
    PARAM_ERROR(10002,"参数错误"),
    DATABASE_ERROR(10003,"数据访问错误"),
    PARAM_ISNULL_EXCEPTION(10004,"参数为空异常"),
    COLLECTION_IS_EMPTY(10005,"参数集合为空"),
    INNNER_PARAM_ERROR(10006,"系统内部参数异常"),
    NOT_ALLOW_OPERATION_ERROR(10007,"系统禁止操作"),
    SERVER_LOCKED_IN_ERROR(10008,"请求服务已锁定"),
    TENANTID_CANNOT_BE_EMPTY(10009,"租户id为空"),
    USER_NOT_LOGIN(10010,"用户未登录"),
    USER_NOT_ROLE(10011,"用户无权限"),
    APPROVAL_ERROR(80001,"审核异常");

    /** 错误码 */
    private int code;

    /** 错误描述 */
    private String msg;

    CommonResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return msg;
    }
}
