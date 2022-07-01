package com.sunfujun.tools.io.core.call.file;

/**
 * Body point object
 * 被替换成为的body里的数据行实体
 */
public class BodyPointObject {

    /**
     * Index line
     * //对应数据行
     */
    private Integer indexLine;

    /**
     * Is send
     * //对应数据行分离集合是否调用接口
     */
    private Integer isSend = 0;

    /**
     * Sleep time
     * //对应数据行分离集合 调用接口休眠时间
     */
    private Long sleepTime = 0L;


    /**
     * Gets index line *
     *
     * @return the index line
     */
    public Integer getIndexLine() {
        return indexLine;
    }

    /**
     * Sets index line *
     *
     * @param indexLine index line
     */
    public void setIndexLine(Integer indexLine) {
        this.indexLine = indexLine;
    }

    /**
     * Gets is send *
     *
     * @return the is send
     */
    public Integer getIsSend() {
        return isSend;
    }

    /**
     * Sets is send *
     *
     * @param isSend is send
     */
    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }
}
