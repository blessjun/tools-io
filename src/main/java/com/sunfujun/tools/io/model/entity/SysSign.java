package com.sunfujun.tools.io.model.entity;


import java.io.Serializable;
import java.util.List;

/**
 * @author scott
 */
public class SysSign implements Serializable {
    private static final long serialVersionUID = 5758892980395530261L;


    private String type;



    private List<SysSignRule> rule;


    /**
     * 0:不用。1.大写。2.小写
     */
    private Integer format;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SysSignRule> getRule() {
        return this.rule;
    }

    public void setRule(List<SysSignRule> rule) {
        this.rule = rule;
    }

    public Integer getFormat() {
        return this.format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }
}
