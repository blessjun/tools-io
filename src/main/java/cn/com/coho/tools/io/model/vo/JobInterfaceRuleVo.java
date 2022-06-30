package cn.com.coho.tools.io.model.vo;


import java.io.Serializable;

public class JobInterfaceRuleVo implements Serializable {

    private static final long serialVersionUID = -8027024673515403423L;

    private Long interfaceId;

    /**
     * 接口使用的规则
     */
    private Long ruleId;

    /**
     * 接口规则类型，入参or出参
     */
    private Integer ruleType;


    private Integer sort;

    public Long getInterfaceId() {
        return this.interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Long getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
