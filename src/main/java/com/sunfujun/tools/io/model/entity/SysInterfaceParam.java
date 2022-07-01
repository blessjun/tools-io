
package com.sunfujun.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * @author scott
 */
@TableName("sys_interface_param")
public class SysInterfaceParam extends BaseEntity {
	
	private static final long serialVersionUID = 1L;


	/**
	*接口id
	*/
	@TableField("interface_id")
	private Long interfaceId = 0L;

	/**
	*参数名称
	*/
	@TableField("name")
	private String name;

	/**
	*参数值
	*/
	@TableField("value")
	private String value;

	/**
	*参数分类: header、params、return、body
	*/
	@TableField("category")
	private String category;

	/**
	*参数类型
	*/
	@TableField("type")
	private Integer type;

	/**
	*参数失效时间
	*/
	@TableField("time")
	private Long time;

	/**
	*参数类型id
	*/
	@TableField("object_id")
	private Long objectId;

	/**
	*是否必填
	*/
	@TableField("is_required")
	private Boolean isRequired;

	/**
	*是否全局参数
	*/
	@TableField("is_sys")
	private Boolean isSys;

	@TableField("description")
	private String description;


	public Long getInterfaceId() {
		return this.interfaceId;
	}

	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Boolean getIsRequired() {
		return this.isRequired;
	}

	public void setIsRequired(Boolean required) {
		this.isRequired = required;
	}

	public Boolean getIsSys() {
		return this.isSys;
	}

	public void setIsSys(Boolean sys) {
		this.isSys = sys;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

