package cn.com.coho.tools.io.core.call.file;

import java.util.HashMap;
import java.util.Map;

/**
 * File param
 * 对应参数标识文件
 */
public class FileParam {

    /**
     * Header
     */
    private Map<String, Object> header = new HashMap<>();
    /**
     * Query
     */
    private Map<String, Object> query = new HashMap<>();
    /**
     * Body
     */
    private Object body;
    /**
     * Other
     */
    private Map<String, Object> other = new HashMap<>();

    /**
     * Gets header *
     *
     * @return the header
     */
    public Map<String, Object> getHeader() {
        return header;
    }

    /**
     * Sets header *
     *
     * @param header header
     */
    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    /**
     * Gets query *
     *
     * @return the query
     */
    public Map<String, Object> getQuery() {
        return query;
    }

    /**
     * Sets query *
     *
     * @param query query
     */
    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    /**
     * Gets body *
     *
     * @return the body
     */
    public Object getBody() {
        return body;
    }

    /**
     * Sets body *
     *
     * @param body body
     */
    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * Gets other *
     *
     * @return the other
     */
    public Map<String, Object> getOther() {
        return other;
    }

    /**
     * Sets other *
     *
     * @param other other
     */
    public void setOther(Map<String, Object> other) {
        this.other = other;
    }
}
