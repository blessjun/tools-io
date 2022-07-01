
package com.sunfujun.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("sys_param")
public class SysParam extends BaseEntity {
	
	private static final long serialVersionUID = 1L;


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


	@TableField("group_id")
	private Long groupId;


	@TableField("group_name")
	private String groupName;

	/**
	*参数分类: header、params
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



	@TableField("description")
	private String description;


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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

