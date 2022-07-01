package com.sunfujun.tools.io.model.dto;


import java.io.Serializable;

public class PageDto implements Serializable {


    private static final long serialVersionUID = 1L;


    private Long pageIndex;


    private Long pageSize;


    private String keyWord;

    public Long getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
