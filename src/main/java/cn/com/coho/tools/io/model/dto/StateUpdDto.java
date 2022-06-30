package cn.com.coho.tools.io.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author scott
 */
public class StateUpdDto implements Serializable {

    private static final long serialVersionUID = 5306990420157256604L;

    private List<Long> ids;


    private Boolean isUse;

    public List<Long> getIds() {
        return this.ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Boolean getIsUse() {
        return this.isUse;
    }

    public void setIsUse(Boolean use) {
        this.isUse = use;
    }
}
