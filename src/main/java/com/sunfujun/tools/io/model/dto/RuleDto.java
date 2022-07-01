package com.sunfujun.tools.io.model.dto;

public class RuleDto extends BaseDto{

    private static final long serialVersionUID = -8456874587861751416L;

    private String name;

    /**
     *rule
     */
    private String rule;

    /**
     *参数描述
     */
    private String description;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
