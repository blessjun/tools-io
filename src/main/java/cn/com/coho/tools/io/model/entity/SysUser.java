
package cn.com.coho.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * @author scott
 */
@TableName("sys_user")
public class SysUser {
	
	private static final long serialVersionUID = 1L;

	@TableId(
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/*
	*登录名
	*/
	@TableField("user_name")
	private String userName;

	/*
	*密码
	*/
	@TableField("password")
	private String password;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

