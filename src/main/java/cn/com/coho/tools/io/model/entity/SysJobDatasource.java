
package cn.com.coho.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("sys_job_datasource")
public class SysJobDatasource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted = false;

    /**
     * 数据源编码
     */
    @TableField("code")
    private String code;

    /**
     * 数据源名称
     */
    @TableField("name")
    private String name;

    /**
     * 数据源类型
     */
    @TableField("type")
    private String type;

    /**
     * 数据源指向实现id
     */
    @TableField("point_object_id")
    private Long pointObjectId;

    /**
     * 1.指向对象名称 映射到文件路径
     */
    @TableField("point_object_name")
    private String pointObjectName;

    /**
     * '指向数据源编码'
     */
    @TableField("point_object_code")
    private String pointObjectCode;


    /**
     * '指向数据内容'
     */
    @TableField("point_object_data")
    private String pointObjectData;

    /**
     * 任务id
     */
    @TableField("job_id")
    private Long jobId;

    /**
     * '任务编码'
     */
    @TableField("job_code")
    private String jobCode;

    /**
     * '作为任务参数类型  1入参，2出参'
     */
    @TableField("job_param_type")
    private Integer jobParamType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPointObjectId() {
        return pointObjectId;
    }

    public void setPointObjectId(Long pointObjectId) {
        this.pointObjectId = pointObjectId;
    }

    public String getPointObjectName() {
        return pointObjectName;
    }

    public void setPointObjectName(String pointObjectName) {
        this.pointObjectName = pointObjectName;
    }

    public String getPointObjectCode() {
        return pointObjectCode;
    }

    public void setPointObjectCode(String pointObjectCode) {
        this.pointObjectCode = pointObjectCode;
    }

    public String getPointObjectData() {
        return pointObjectData;
    }

    public void setPointObjectData(String pointObjectData) {
        this.pointObjectData = pointObjectData;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public Integer getJobParamType() {
        return jobParamType;
    }

    public void setJobParamType(Integer jobParamType) {
        this.jobParamType = jobParamType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}

