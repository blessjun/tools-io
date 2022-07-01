package com.sunfujun.tools.io.core.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunfujun.tools.io.core.util.ResponseCode;
import com.sunfujun.tools.io.model.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;


/**
 * @author scott
 * 统一异常捕获处理
 */
@RestControllerAdvice
public class ExceptionConfigController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionConfigController.class);

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result nullPointerExceptionHandler(NullPointerException e) {
        log.error("空指针异常:", e);
        return Result.newInstance(ResponseCode.NOT_POINT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(RestClientException e) {
        log.error("系统异常:", e);
        return Result.newInstance(ResponseCode.INTERFACE_ERROR);
    }


    @ExceptionHandler(RestClientException.class)
    @ResponseBody
    public Result runtimeExceptionHandler(RestClientException e) {
        log.error("接口调用异常:", e);
        return Result.newInstance(ResponseCode.INTERFACE_ERROR);
    }

    /**
     * 捕获和处理自定义的异常
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Result exceptionHandler(BizException e) {
        log.error("业务异常:", e);
        return new Result<>(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    public Result jsonProcessingExceptionHandler(NullPointerException e) {
        log.error("json解析异常:", e);
        return Result.newInstance(ResponseCode.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result runtimeExceptionHandler(RuntimeException e) {
        log.error("系统运行时发生异常:", e);
        return new Result<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

}
