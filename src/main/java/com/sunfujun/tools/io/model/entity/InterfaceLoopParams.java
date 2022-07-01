package com.sunfujun.tools.io.model.entity;

import java.io.Serializable;

public class InterfaceLoopParams implements Serializable {

    private static final long serialVersionUID = 1L;


    private InterfaceLoopParamDetail pageNo;


    private InterfaceLoopParamDetail pageSize;


    private InterfaceLoopParamDetail pageTotal;

    public InterfaceLoopParamDetail getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(InterfaceLoopParamDetail pageNo) {
        this.pageNo = pageNo;
    }

    public InterfaceLoopParamDetail getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(InterfaceLoopParamDetail pageSize) {
        this.pageSize = pageSize;
    }

    public InterfaceLoopParamDetail getPageTotal() {
        return this.pageTotal;
    }

    public void setPageTotal(InterfaceLoopParamDetail pageTotal) {
        this.pageTotal = pageTotal;
    }
}

