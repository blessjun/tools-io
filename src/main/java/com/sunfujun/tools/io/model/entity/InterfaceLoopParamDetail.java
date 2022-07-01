package com.sunfujun.tools.io.model.entity;

import java.io.Serializable;

/**
 * @author scott
 */
public class InterfaceLoopParamDetail implements Serializable {

    private static final long serialVersionUID = 1L;


    private String cate;


    private String node;


    private String value;

    public String getCate() {
        return this.cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getNode() {
        return this.node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

