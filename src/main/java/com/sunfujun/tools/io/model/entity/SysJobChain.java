
package com.sunfujun.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("sys_job_chain")
public class SysJobChain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务链编码
     */
    @TableField("code")
    private String code;

    /**
     * 任务链名称
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
     * 任务id集合
     */
    @TableField("job_id")
    private String jobIdList;

    /**
     * 任务编码集合
     */
    @TableField("job_code")
    private String jobCodeList;

    /**
     * 任务名称集合
     */
    @TableField("job_name")
    private String jobNameList;

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
     * 任务执行超时时间
     */
    @TableField("job_timeout")
    private Integer jobTimeout;


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

    public String getJobIdList() {
        return jobIdList;
    }

    public void setJobIdList(String jobIdList) {
        this.jobIdList = jobIdList;
    }

    public String getJobCodeList() {
        return jobCodeList;
    }

    public void setJobCodeList(String jobCodeList) {
        this.jobCodeList = jobCodeList;
    }

    public String getJobNameList() {
        return jobNameList;
    }

    public void setJobNameList(String jobNameList) {
        this.jobNameList = jobNameList;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(Integer jobTimeout) {
        this.jobTimeout = jobTimeout;
    }
}

