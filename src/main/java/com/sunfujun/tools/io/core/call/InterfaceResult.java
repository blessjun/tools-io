package com.sunfujun.tools.io.core.call;

import com.sunfujun.tools.io.model.entity.SysInterfaceLog;

import java.util.List;

/**
 * @author scott
 * 接口返回结果
 */
public class InterfaceResult {


    private Boolean isSuccess;


    //接口调用返回记录
    private List<SysInterfaceLog> logList;

    public InterfaceResult() {
    }

    public InterfaceResult(Boolean isSuccess, List<SysInterfaceLog> logList) {
        this.isSuccess = isSuccess;
        this.logList = logList;
    }


    public Boolean getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(Boolean success) {
        this.isSuccess = success;
    }

    public List<SysInterfaceLog> getLogList() {
        return this.logList;
    }

    public void setLogList(List<SysInterfaceLog> logList) {
        this.logList = logList;
    }
}
