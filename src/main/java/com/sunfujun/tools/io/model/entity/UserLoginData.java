package com.sunfujun.tools.io.model.entity;

/**
 * @author scott
 */
public class UserLoginData{

	private Long userId;

	private String userName;

	public UserLoginData() {
	}

	public UserLoginData(Long userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}