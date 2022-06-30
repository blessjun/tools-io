package cn.com.coho.tools.io.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author scott
 */

public class JobChainVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
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
     * 任务id集合
     */
    private String jobIdList;

    /**
     * 任务编码集合
     */
    private String jobCodeList;

    /**
     * 任务名称集合
     */
    private String jobNameList;

    /**
     * 任务集合
     */
    private List<JobInfoVo> jobInfoVoList;

    /**
     * 超时时间
     */
    private Long jobTimeout;


    /**
     * 任务描述
     */
    private String jobDesc;


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

    public List<JobInfoVo> getJobInfoVoList() {
        return jobInfoVoList;
    }

    public void setJobInfoVoList(List<JobInfoVo> jobInfoVoList) {
        this.jobInfoVoList = jobInfoVoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobTimeout() {
        return jobTimeout;
    }

    public void setJobTimeout(Long jobTimeout) {
        this.jobTimeout = jobTimeout;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
