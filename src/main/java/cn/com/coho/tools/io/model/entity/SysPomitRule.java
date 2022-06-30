
package cn.com.coho.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * @author scott
 */
@TableName("sys_pomit_rule")
public class SysPomitRule extends BaseEntity {
	
	private static final long serialVersionUID = 1L;


	/**
	*name
	*/
	@TableField("name")
	private String name;

	/**
	*rule
	*/
	@TableField("rule")
	private String rule;

	/**
	*参数描述
	*/
	@TableField("description")
	private String description;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

