
package cn.com.coho.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * @author scott
 */
@TableName("sys_interface_info")
public class SysInterfaceInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;


	/**
	*接口分组
	*/
	@TableField("group_id")
	private Long groupId;


	@TableField("group_name")
	private String groupName;

	@TableField("code")
	private String code;

	/**
	*接口名称
	*/
	@TableField("name")
	private String name;

	/**
	*接口路径
	*/
	@TableField("path")
	private String path;

	/**
	*接口类型，1.业务接口 ，2.功能接口
	*/
	@TableField("type")
	private Integer type;

	/**
	*是否循环调用接口
	*/
	@TableField("is_loop")
	private Boolean isLoop;

	@TableField("loop_param")
	private String loopParam;

	@TableField("status")
	private Boolean status;

	/**
	*是否启用
	*/
	@TableField("is_use")
	private Boolean isUse;


	@TableField("description")
	private String description;


	@TableField(exist = false)
	private Boolean isOne;


	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public String getLoopParam() {
		return this.loopParam;
	}

	public void setLoopParam(String loopParam) {
		this.loopParam = loopParam;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public Boolean getIsOne() {
		return this.isOne;
	}

	public void setIsOne(Boolean one) {
		this.isOne = one;
	}
}

