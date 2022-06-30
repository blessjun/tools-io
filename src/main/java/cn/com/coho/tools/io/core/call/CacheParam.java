package cn.com.coho.tools.io.core.call;

import java.io.Serializable;

/**
 * 接口返回缓存参数通用
 *
 * @author scott
 */
public class CacheParam implements Serializable {

    private static final long serialVersionUID = -1386048266058635706L;


    private String name;


    private String value;


    private Long time;

    /**
     * Cache param
     */
    public CacheParam() {
    }

    /**
     * Cache param
     *
     * @param name  name
     * @param value value
     */
    public CacheParam(String name, String value) {
        this.name = name;
        this.value = value;
        this.time = 0L;
    }

    /**
     * Cache param
     *
     * @param name  name
     * @param value value
     * @param time  time
     */
    public CacheParam(String name, String value, Long time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }

    /**
     * Gets name *
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name *
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets value *
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value *
     *
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets time *
     *
     * @return the time
     */
    public Long getTime() {
        return time;
    }

    /**
     * Sets time *
     *
     * @param time time
     */
    public void setTime(Long time) {
        this.time = time;
    }
}
