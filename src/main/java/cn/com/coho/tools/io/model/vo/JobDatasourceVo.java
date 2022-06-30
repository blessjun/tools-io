
package cn.com.coho.tools.io.model.vo;

import java.io.Serializable;

public class JobDatasourceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据源编码
     */

    private Long id;

    /**
     * 数据源编码
     */

    private String code;
    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源类型
     */

    private String type;

    /**
     * 数据源指向实现id
     */

    private Long pointObjectId;

    /**
     * 1.指向对象名称 映射到文件路径
     */

    private String pointObjectName;

    /**
     * '指向数据源编码'
     */

    private String pointObjectCode;


    /**
     * '指向数据内容'
     */

    private String pointObjectData;

    /**
     * 任务id
     */

    private Long jobId;

    /**
     * '任务编码'
     */

    private String jobCode;

    /**
     * '作为任务参数类型  1入参，2出参'
     */

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

