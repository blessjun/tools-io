package cn.com.coho.tools.io.model.dto;

public class InterfaceSearchDto extends PageDto{

    private static final long serialVersionUID = 1L;

    private Long groupId;

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
