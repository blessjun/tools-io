package cn.com.coho.tools.io.model.dto;

import cn.com.coho.tools.io.core.util.Identities;

import java.io.Serializable;

/**
 * @author scott
 */
public class BaseDto implements Serializable {


    private static final long serialVersionUID = -1L;

    private Long id;


    public Long getId() {
        return id == null || id == 0L ? Identities.randomLong():id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
