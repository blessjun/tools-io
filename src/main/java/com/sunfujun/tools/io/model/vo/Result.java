package com.sunfujun.tools.io.model.vo;

import com.sunfujun.tools.io.core.util.ResponseCode;

/**
 * @author scott
 * @desc 接口返回通用类
 */
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    private Integer total;

    public Result(Integer code, String msg, T data, Integer total) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static <T> Result<T> newInstance(ResponseCode responseCode, T data) {
        return new Result(responseCode.getCode(), responseCode.getMsg(), data);
    }

    public static <T> Result<T> newInstance(ResponseCode responseCode, T data, Integer total) {
        return new Result(responseCode.getCode(), responseCode.getMsg(), data, total);
    }

    public static <T> Result<T> buildSuccess(T data, Integer total) {
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data, total);
    }


    public static <T> Result<T> newInstance(ResponseCode responseCode) {
        return new Result(responseCode.getCode(), responseCode.getMsg());
    }

    public static <T> Result<T> buildSuccess(T data) {
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    public static Result buildError() {
        return new Result(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR.getMsg());
    }
}
