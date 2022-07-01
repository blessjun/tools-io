package com.sunfujun.tools.io.model.dto;

import java.util.List;

/**
 * @author scott
 */
public class JobDto extends BaseDto {

    private static final long serialVersionUID = 5452162252465456800L;

    /**
     * 任务链id
     */
    private Long jobChainId;

    /**
     * 任务编码
     */
    private String code;

    /**
     * 任务名称
     */
    private String name;

    /**
     * cron表达式
     */
    private String jobCron;

    /**
     * 1.开启，0.关闭
     */

    private Boolean status;

    /**
     * 超时时间
     */
    private Long jobTimeout;

    /**
     * 备注
     */
    private String remark;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * '任务在任务链的顺序'
     */
    private Integer sort;

    /**
     * '规则id'
     */
    private Long ruleId;

    /**
     * '规则编码'
     */
    private String ruleCode;


    private List<JobDatasourceDto> jobDatasourceList;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobCron() {
        return this.jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getJobTimeout() {
        return this.jobTimeout;
    }

    public void setJobTimeout(Long jobTimeout) {
        this.jobTimeout = jobTimeout;
    }

    public String getJobDesc() {
        return this.jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public List<JobDatasourceDto> getJobDatasourceList() {
        return jobDatasourceList;
    }

    public void setJobDatasourceList(List<JobDatasourceDto> jobDatasourceList) {
        this.jobDatasourceList = jobDatasourceList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getJobChainId() {
        return jobChainId;
    }

    public void setJobChainId(Long jobChainId) {
        this.jobChainId = jobChainId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }
}
