package com.sunfujun.tools.io.model.dto;

import com.sunfujun.tools.io.model.entity.InterfaceLoopParams;

import java.util.List;

/**
 * @author scott
 */
public class InterfaceDto extends BaseDto {

    private static final long serialVersionUID = 6161452507695042721L;


    /**
     *接口分组
     */
    private Long groupId;


    private String code;

    /**
     *接口名称
     */
    private String name;

    /**
     *接口路径
     */
    private String path;

    /**
     *接口类型，1.业务接口 ，2.功能接口
     */
    private Integer type;

    /**
     *是否循环调用接口
     */
    private Boolean isLoop;


    private InterfaceLoopParams loopParams;

    /**
     *是否启用
     */
    private Boolean isUse;


    private Boolean isOne;


    private String description;


    private List<InterfaceParamDto> params;

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsLoop() {
        return this.isLoop;
    }

    public void setIsLoop(Boolean loop) {
        this.isLoop = loop;
    }

    public InterfaceLoopParams getLoopParams() {
        return this.loopParams;
    }

    public void setLoopParams(InterfaceLoopParams loopParams) {
        this.loopParams = loopParams;
    }

    public Boolean getIsUse() {
        return this.isUse;
    }

    public void setIsUse(Boolean use) {
        this.isUse = use;
    }

    public Boolean getIsOne() {
        return this.isOne;
    }

    public void setIsOne(Boolean one) {
        this.isOne = one;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InterfaceParamDto> getParams() {
        return this.params;
    }

    public void setParams(List<InterfaceParamDto> params) {
        this.params = params;
    }
}
