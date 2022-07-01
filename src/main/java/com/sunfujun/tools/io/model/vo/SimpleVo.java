package com.sunfujun.tools.io.model.vo;


import java.util.List;

public class SimpleVo {



    private Long id;


    private String code;


    private String name;


    private List<String> cacheNames;


    public SimpleVo() {
    }

    public SimpleVo(Long id, String code, String name, List<String> cacheNames) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.cacheNames = cacheNames;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCacheNames() {
        return this.cacheNames;
    }

    public void setCacheNames(List<String> cacheNames) {
        this.cacheNames = cacheNames;
    }
}
