package com.sunfujun.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author scott
 */
@TableName("sys_interface_group")
public class SysInterfaceGroup extends BaseEntity{


    private static final long serialVersionUID = -4655390088166397727L;


    @TableField("name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
