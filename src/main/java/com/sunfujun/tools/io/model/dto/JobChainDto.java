package com.sunfujun.tools.io.model.dto;

import java.util.Date;
import java.util.List;

/**
 * @author scott
 */
public class JobChainDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    /**
     * 任务链编码
     */
    private String code;

    /**
     * 任务链名称
     */
    private String name;

    /**
     * cron表达式
     */
    private String jobCron;

    /**
     * 1.开启，0.关闭
     */
    private Boolean status = false;


    /**
     * 任务名称集合
     */
    private List<JobDto> jobDtoList;

    /**
     * 超时时间
     */
    private Long jobTimeout;

    /**
     * 下次调度时间
     */
    private Date triggerNextTime;

    /**
     * 上次调度时间
     */
    private Date triggerLastTime;


    /**
     * 备注
     */
    private String remark;

    /**
     * 任务描述
     */
    private String jobDesc;

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

    public List<JobDto> getJobDtoList() {
        return jobDtoList;
    }

    public void setJobDtoList(List<JobDto> jobDtoList) {
        this.jobDtoList = jobDtoList;
    }

    public Long getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(Long jobTimeout) {
        this.jobTimeout = jobTimeout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
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
}
