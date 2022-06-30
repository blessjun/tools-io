package cn.com.coho.tools.io.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author scott
 */

public class JobInfoVo implements Serializable {

    private static final long serialVersionUID = 8119634151224927774L;

    private Long id;
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


    private List<JobDatasourceVo> JobDatasourceList;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getTriggerNextTime() {
        return this.triggerNextTime;
    }

    public void setTriggerNextTime(Date triggerNextTime) {
        this.triggerNextTime = triggerNextTime;
    }

    public Date getTriggerLastTime() {
        return this.triggerLastTime;
    }

    public void setTriggerLastTime(Date triggerLastTime) {
        this.triggerLastTime = triggerLastTime;
    }

    public List<JobDatasourceVo> getJobDatasourceList() {
        return JobDatasourceList;
    }

    public void setJobDatasourceList(List<JobDatasourceVo> jobDatasourceList) {
        JobDatasourceList = jobDatasourceList;
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
