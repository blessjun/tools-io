package cn.com.coho.tools.io.core.call.file;

import cn.com.coho.tools.io.model.vo.JobDatasourceVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * File param object
 * 传入groovy 的文件对象
 */
public class FileParamObject {
    /**
     * File path
     * 文件路径对象
     */
    private FilePath filePath;
    /**
     * Data
     * 数据文件对应
     */
    private JSONArray data = new JSONArray();
    /**
     * Param
     * //如果是参数类型就没有data数据
     */
    private JSONObject param = new JSONObject();
    /**
     * Data line num
     * //-1 全部读取 对应参数文件
     */
    private int dataLineNum = 1;

    /**
     * Job datasource vo
     * 数据源
     */
    private JobDatasourceVo jobDatasourceVo;

    /**
     * Gets file path *
     *
     * @return the file path
     */
    public FilePath getFilePath() {
        return filePath;
    }

    /**
     * Sets file path *
     *
     * @param filePath file path
     */
    public void setFilePath(FilePath filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets data *
     *
     * @return the data
     */
    public JSONArray getData() {
        return data;
    }

    /**
     * Sets data *
     *
     * @param data data
     */
    public void setData(JSONArray data) {
        this.data = data;
    }

    /**
     * Gets data line num *
     *
     * @return the data line num
     */
    public int getDataLineNum() {
        return dataLineNum;
    }

    /**
     * Sets data line num *
     *
     * @param dataLineNum data line num
     */
    public void setDataLineNum(int dataLineNum) {
        this.dataLineNum = dataLineNum;
    }

    /**
     * Gets param *
     *
     * @return the param
     */
    public JSONObject getParam() {
        return param;
    }

    /**
     * Sets param *
     *
     * @param param param
     */
    public void setParam(JSONObject param) {
        this.param = param;
    }

    /**
     * Gets job datasource vo *
     *
     * @return the job datasource vo
     */
    public JobDatasourceVo getJobDatasourceVo() {
        return jobDatasourceVo;
    }

    /**
     * Sets job datasource vo *
     *
     * @param jobDatasourceVo job datasource vo
     */
    public void setJobDatasourceVo(JobDatasourceVo jobDatasourceVo) {
        this.jobDatasourceVo = jobDatasourceVo;
    }
}
