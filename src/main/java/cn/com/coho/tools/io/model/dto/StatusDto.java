package cn.com.coho.tools.io.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author scott
 */
public class StatusDto implements Serializable {

    private static final long serialVersionUID = 5306990420157256604L;

    private List<Long> ids;


    private Boolean isStart;

    public List<Long> getIds() {
        return this.ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Boolean getIsStart() {
        return this.isStart;
    }

    public void setIsStart(Boolean start) {
        this.isStart = start;
    }
}
