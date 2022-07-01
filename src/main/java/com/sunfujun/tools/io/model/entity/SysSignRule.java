
package com.sunfujun.tools.io.model.entity;

import java.io.Serializable;


/**
 * @author scott
 */
public class SysSignRule implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private String value;

	private Boolean isParam;

	private Integer order;

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getIsParam() {
		return this.isParam;
	}

	public void setIsParam(Boolean param) {
		this.isParam = param;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}

