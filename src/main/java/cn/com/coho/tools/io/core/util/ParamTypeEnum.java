package cn.com.coho.tools.io.core.util;

/**
 * @author scott
 * 参数类型枚举类
 */

public enum ParamTypeEnum {
    INTEGER(0),STRING(1),BOOLEAN(2),LONG(3),DATE(4),TIMESTAMP(5),INTERFACE(6),FLAG(7),SIGN(8),CACHE(9),RULE(10);


    private Integer type;

    ParamTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static ParamTypeEnum get(Integer type){
        for (ParamTypeEnum paramTypeEnum: values()){
            if (paramTypeEnum.getType().equals(type)) {
                return paramTypeEnum;
            }
        }
        return STRING;
    }
}
