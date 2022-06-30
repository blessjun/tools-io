
package cn.com.coho.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;


@TableName("sys_job_log")
public class SysJobLog extends BaseEntity {
	
	private static final long serialVersionUID = 1L;


	/*
	*任务id
	*/
	@TableField("job_id")
	private Long jobId;

	/*
	*触发时间，执行时间
	*/
	@TableField("trigger_time")
	private Date triggerTime;

	/*
	*对应接口执行日志
	*/
	@TableField("interface_log_ids")
	private String interfaceLogIds;

	/*
	*接口执行结果
	*/
	@TableField("job_result")
	private String jobResult;

	

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Date getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}

	public String getInterfaceLogIds() {
		return interfaceLogIds;
	}

	public void setInterfaceLogIds(String interfaceLogIds) {
		this.interfaceLogIds = interfaceLogIds;
	}

	public String getJobResult() {
		return jobResult;
	}

	public void setJobResult(String jobResult) {
		this.jobResult = jobResult;
	}

}

