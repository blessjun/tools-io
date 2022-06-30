package cn.com.coho.tools.io.model.vo;

import cn.com.coho.tools.io.model.entity.InterfaceLoopParams;
import cn.com.coho.tools.io.model.entity.SysInterfaceLog;
import cn.com.coho.tools.io.model.entity.SysInterfaceParam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author scott
 */
public class InterfaceInfoVo implements Serializable {

    private static final long serialVersionUID = 4862973057702781672L;



    private Long id;
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



    private Boolean Status;


    /**
     *是否循环调用接口
     */
    private Boolean isLoop;


    /**
     * 循环参数
     */
    private Map<String, InterfaceLoopParams> loopParams;
    /**
     *请求返回结构体
     */
    private SysInterfaceLog response;

    /**
     *是否启用
     */
    private Boolean isUse;


    private String description;


    private Map<String, List<SysInterfaceParam>> params;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getIsStatus() {
        return this.Status;
    }

    public void setIsStatus(Boolean status) {
        this.Status = status;
    }

    public Boolean getIsLoop() {
        return this.isLoop;
    }

    public void setIsLoop(Boolean loop) {
        this.isLoop = loop;
    }

    public Map<String, InterfaceLoopParams> getLoopParams() {
        return this.loopParams;
    }

    public void setLoopParams(Map<String, InterfaceLoopParams> loopParams) {
        this.loopParams = loopParams;
    }

    public SysInterfaceLog getResponse() {
        return this.response;
    }

    public void setResponse(SysInterfaceLog response) {
        this.response = response;
    }

    public Boolean getIsUse() {
        return this.isUse;
    }

    public void setIsUse(Boolean use) {
        this.isUse = use;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, List<SysInterfaceParam>> getParams() {
        return this.params;
    }

    public void setParams(Map<String, List<SysInterfaceParam>> params) {
        this.params = params;
    }
}
