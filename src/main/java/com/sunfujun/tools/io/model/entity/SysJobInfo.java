
package com.sunfujun.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("sys_job_info")
public class SysJobInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务编码
     */
    @TableField("code")
    private String code;

    /**
     * 任务名称
     */
    @TableField("name")
    private String name;

    /**
     * cron表达式
     */
    @TableField("job_cron")
    private String jobCron;

    /**
     * 1.开启，0.关闭
     */
    @TableField("status")
    private Boolean status = false;

    /**
     * 任务执行超时时间
     */
    @TableField("job_timeout")
    private Integer jobTimeout;

    /**
     * 下次调度时间
     */
    @TableField("trigger_next_time")
    private Date triggerNextTime;

    /**
     * 上次调度时间
     */
    @TableField("trigger_last_time")
    private Date triggerLastTime;

    /**
     * 任务描述
     */
    @TableField("job_desc")
    private String jobDesc;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * '规则id'
     */
    @TableField("rule_id")
    private Long ruleId;

    /**
     * '规则编码'
     */
    @TableField("rule_code")
    private String ruleCode;

    /**
     * '任务在任务链的顺序'
     */
    @TableField("sort")
    private Integer sort;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(Integer jobTimeout) {
        this.jobTimeout = jobTimeout;
    }

    public Date getTriggerNextTime() {
        return triggerNextTime;
    }

    public void setTriggerNextTime(Date triggerNextTime) {
        this.triggerNextTime = triggerNextTime;
    }

    public Date getTriggerLastTime() {
        return triggerLastTime;
    }

    public void setTriggerLastTime(Date triggerLastTime) {
        this.triggerLastTime = triggerLastTime;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

