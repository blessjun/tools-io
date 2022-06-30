package cn.com.coho.tools.io.core.call.file;

import cn.com.coho.tools.io.model.vo.JobDatasourceVo;
import cn.com.coho.tools.io.model.vo.JobInfoVo;
import org.apache.commons.lang3.StringUtils;

/**
 * Job file path manage
 * 任务 生成文件路径，文件key的管理
 */
public class JobFilePathManage {

    /**
     * root
     */
    private static String root = "job";
    /**
     * tenantCode
     */
    private static String tenantCode = "coho";
    /**
     * POINT_DATA
     */
    public static final String POINT_DATA = "$pointData";

    /**
     * Gets path *
     *
     * @param jobDatasourceVo job datasource vo
     * @param jobInfoVo       job info vo
     * @return the path
     */
    private static StringBuilder getPath(JobDatasourceVo jobDatasourceVo, JobInfoVo jobInfoVo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/");
        stringBuilder.append(jobInfoVo.getId());
        stringBuilder.append("_");
        stringBuilder.append(jobInfoVo.getCode());
        stringBuilder.append("/");
        stringBuilder.append(jobDatasourceVo.getId());
        stringBuilder.append("_");
        stringBuilder.append(jobDatasourceVo.getCode());
        stringBuilder.append("/");
        stringBuilder.append(jobDatasourceVo.getName());
        return stringBuilder;
    }

    /**
     * Gets path param *
     *
     * @param jobDatasourceVo job datasource vo
     * @param jobInfoVo       job info vo
     * @return the path param
     * //获取数据源指向参数路径
     */
    public static FilePath getPathParam(JobDatasourceVo jobDatasourceVo, JobInfoVo jobInfoVo) {
        StringBuilder stringBuilder = getPath(jobDatasourceVo, jobInfoVo);
        stringBuilder.append(".param");
        FilePath filePath = new FilePath();
        filePath.setRoot(root);
        filePath.setTenantCode(tenantCode);
        filePath.setPath(stringBuilder.toString());
        return filePath;
    }

    /**
     * Gets path body *
     *
     * @param jobDatasourceVo job datasource vo
     * @param jobInfoVo       job info vo
     * @return the path body
     * //获取数据源指向内容路径
     */
    public static FilePath getPathBody(JobDatasourceVo jobDatasourceVo, JobInfoVo jobInfoVo) {
        StringBuilder stringBuilder = getPath(jobDatasourceVo, jobInfoVo);
        stringBuilder.append("_");
        stringBuilder.append(POINT_DATA);
        stringBuilder.append("_");
        stringBuilder.append(".data");
        FilePath filePath = new FilePath();
        filePath.setRoot(root);
        filePath.setTenantCode(tenantCode);
        filePath.setPath(stringBuilder.toString());
        return filePath;
    }

    /**
     * Gets path dir *
     *
     * @param jobDatasourceVo job datasource vo
     * @param jobInfoVo       job info vo
     * @return the path dir
     * //获取数据源目录
     */
    public static FilePath getPathDir(JobDatasourceVo jobDatasourceVo, JobInfoVo jobInfoVo) {
        StringBuilder stringBuilder = getPath(jobDatasourceVo, jobInfoVo);
        FilePath filePath = new FilePath();
        filePath.setRoot(root);
        filePath.setTenantCode(tenantCode);
        filePath.setPath(stringBuilder.toString());
        return filePath;
    }

    /**
     * Gets param key *
     *
     * @param jobDatasourceVo job datasource vo
     * @return the param key
     * //获取文件参数key
     */
    public static String getParamKey(JobDatasourceVo jobDatasourceVo) {
        return jobDatasourceVo.getName().concat("_param");
    }

    /**
     * Gets data key *
     *
     * @param jobDatasourceVo job datasource vo
     * @param pointData       point data
     * @return the data key
     * //获取data参数key
     */
    public static String getDataKey(JobDatasourceVo jobDatasourceVo, String pointData) {
        String replace = StringUtils.replace(pointData, ".", "_");
        return jobDatasourceVo.getName().concat("_").concat(replace).concat("_data");
    }


}
