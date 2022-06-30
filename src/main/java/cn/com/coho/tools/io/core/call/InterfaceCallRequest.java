package cn.com.coho.tools.io.core.call;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 接口调用通用请求类
 * @author scott
 */
public class InterfaceCallRequest implements Serializable {

    private static final long serialVersionUID = -1386048266058635706L;


    //接口id
    private Long id;

    //记录调用描述及额外信息
    private String describe;

    private String name;

    private String url;

    private Integer type;

    private Map<String, Object> header;

    private Map<String, Object> query;

    private String body;

    //是否只调用一次
    private Boolean isOne = false;

    //是否循环调用
    private Boolean isLoop;

    //循环调用参数
    private Map<String, Object> looParam;

    //接口成功标识
    private Map<String, Object> flag;

    //接口缓存参数
    private List<CacheParam> cache;

    public InterfaceCallRequest() {
    }

    public InterfaceCallRequest(Long id, String name, String url, Integer type, Boolean isOne,Boolean isLoop, Map<String, Object> header, Map<String, Object> query, String body, Map<String, Object> flag, List<CacheParam> cache) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.isOne = isOne;
        this.header = header;
        this.query = query;
        this.body = body;
        this.flag = flag;
        this.cache = cache;
        this.isLoop = isLoop;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, Object> getHeader() {
        return this.header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Map<String, Object> getQuery() {
        return this.query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIsOne() {
        return this.isOne;
    }

    public void setIsOne(Boolean one) {
        this.isOne = one;
    }

    public Boolean getIsLoop() {
        return this.isLoop;
    }

    public void setIsLoop(Boolean loop) {
        this.isLoop = loop;
    }

    public Map<String, Object> getLooParam() {
        return this.looParam;
    }

    public void setLooParam(Map<String, Object> looParam) {
        this.looParam = looParam;
    }

    public Map<String, Object> getFlag() {
        return this.flag;
    }

    public void setFlag(Map<String, Object> flag) {
        this.flag = flag;
    }

    public List<CacheParam> getCache() {
        return this.cache;
    }

    public void setCache(List<CacheParam> cache) {
        this.cache = cache;
    }
}
