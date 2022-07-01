package com.sunfujun.tools.io.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * @author scott
 */

public class BaseEntity implements Serializable {


    private static final long serialVersionUID = 647825159383346249L;

    @TableId(
            type = IdType.ASSIGN_ID
    )
    private Long id;



    @TableLogic
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField(
            value = "created",
            fill = FieldFill.INSERT
    )
    private Date created;

    @TableField(
            value = "modified",
            fill = FieldFill.INSERT_UPDATE
    )
    private Date modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonIgnore
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @JsonIgnore
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
