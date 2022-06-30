
package cn.com.coho.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;


@TableName("sys_interface_log")
public class SysInterfaceLog extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 接口id
     */
    @TableField("interface_id")
    private Long interfaceId;

    /**
     * 请求json
     */
    @TableField("request_json")
    private String requestJson;

    /**
     * response_header
     */
    @TableField("response_headers")
    private String responseHeaders;

    /**
     * 返回json
     */
    @TableField("response_body")
    private String responseBody;

    /**
     * 状态码
     */
    @TableField("status_code")
    private Integer statusCode;

    /**
     * 调用时间
     */
    @TableField("call_time")
    private Date callTime;

    /**
     * 调用耗时(ms)
     */
    @TableField("consume_time")
    private Long consumeTime;

    /**
     * 调用结果
     */
    @TableField("is_success")
    private Boolean isSuccess;

    /**
     * 调用结果
     */
    @TableField("call_description")
    private String callDescription;


    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public String getResponseHeader() {
        return responseHeaders;
    }

    public void setResponseHeader(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    public SysInterfaceLog() {

    }

    public SysInterfaceLog(Long interfaceId, String requestJson, String responseHeaders, String responseBody, Integer statusCode, Date callTime, Long consumeTime, Boolean isSuccess) {
        this.interfaceId = interfaceId;
        this.requestJson = requestJson;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.statusCode = statusCode;
        this.callTime = callTime;
        this.consumeTime = consumeTime;
        this.isSuccess = isSuccess;
    }
}

