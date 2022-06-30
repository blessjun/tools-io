
package cn.com.coho.tools.io.model.dto;

/**
 * @author scott
 */
public class ParamDto extends BaseDto {
	
	private static final long serialVersionUID = 1L;
	
	/**
	*参数名称
	*/
	private String name;

	/**
	*参数值
	*/
	private String value;

	/**
	*参数分类: header、params
	*/
	private String category;

	/**
	*参数类型
	*/
	private Integer type;

	/**
	*参数失效时间
	*/
	private Long time = 0L;

	/**
	*参数类型id
	*/
	private Long objectId;
	

	private Long groupId;


	private String groupName;

	/**
	*是否必填
	*/
	private Boolean isRequired;



	private String description;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getIsRequired() {
		return this.isRequired;
	}

	public void setIsRequired(Boolean required) {
		this.isRequired = required;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

