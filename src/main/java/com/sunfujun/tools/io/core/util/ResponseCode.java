package com.sunfujun.tools.io.core.util;

/**
 * @author 返回码
 */
public enum ResponseCode {

    //业务
    SUCCESS(200, "调用成功"),
    FAIL(400, "操作失败"),
    UNAUTHORIZED(401, "登录凭证过期!"),
    FORBIDDEN(403, "没有访问权限!"),
    NOT_FOUND(404, "查询不到数据!"),
    PARAM_FAIL(405, "请求参数有误，请检查参数后重试"),
    ERROR_AUTH(406, "身份认证失败"),
    NOT_PERMISSION(407, "权限不足"),
    LOGIN_PARAM_NULL(408, "用户名或密码为空"),
    LOGIN_USER_NOT_FOUND(409, "用户不存在"),
    LOGIN_FAIL(409, "登录失败, 用户名或密码不正确"),
    CODE_REPEAT(300, "编码重复，请保持编码唯一"),
    NAME_REPEAT(301, "名称重复，请保持名称唯一"),
    SAVE_FAIL(410, "数据添加失败"),
    JOB_SCHEDULE_ERROR(449, "任务调度错误"),
    INTERFACE_ERROR(302, "接口调用异常"),
    GROUP_NOT_FOUND(411, "接口分组不存在"),
    //系统
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVICE_UNAVAILABLE(503, "服务器正忙，请稍后再试!"),
    NOT_POINT(505, "系统出现空指针!"),
    JSON_PARSE_ERROR(504, "json解析异常!"),
    // 未知异常
    UNKNOWN(10000, "未知异常!");

    private Integer code;

    private String msg;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
