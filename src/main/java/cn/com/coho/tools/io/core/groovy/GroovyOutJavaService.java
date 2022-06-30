package cn.com.coho.tools.io.core.groovy;


import cn.com.coho.tools.io.core.call.file.BodyPointObject;
import cn.com.coho.tools.io.core.call.file.FileParam;
import cn.com.coho.tools.io.core.call.file.FileParamObject;
import cn.com.coho.tools.io.core.call.file.FilePath;
import cn.com.coho.tools.io.core.exception.BizException;
import cn.com.coho.tools.io.core.util.FileUtils;
import cn.com.coho.tools.io.core.util.JsonObjectUtils;
import cn.com.coho.tools.io.service.SysInterfaceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用于处理脚本里面的调用java
 * Created by  on 2019/9/11.
 */
@Service
public class GroovyOutJavaService implements IScriptService {
    /**
     * log
     */
    private static final Logger log = LoggerFactory.getLogger(GroovyOutJavaService.class);

    /**
     * Groovy script engine
     */
    @Resource
    private GroovyScriptEngine groovyScriptEngine;

    /**
     * Sys interface service
     * 接口执行
     */
    @Autowired
    private SysInterfaceService sysInterfaceService;

    /**
     * Gets one *
     *
     * @param fileParamObject file param object
     * @param bodyPointObject body point object
     * @return the one
     * //读取一个被替换的文件
     */
    public Object getOne(FileParamObject fileParamObject, BodyPointObject bodyPointObject) {
        int indexLine = bodyPointObject.getIndexLine();
        int dataLineNum = fileParamObject.getDataLineNum();
        int index = indexLine - dataLineNum;
        JSONArray data = fileParamObject.getData();
        //如果越界就去读取
        if (index > data.size() - 1) {
            fileParamObject.setDataLineNum(indexLine);
            readerFile(fileParamObject);
            index = 0;
        }
        //如果没读到报错
        if (data.size() == 0) {
            throw new BizException("当前文件行读取不到：".concat(JSON.toJSONString(fileParamObject)));
        }
        return data.get(index);
    }

    /**
     * Reader file *
     *
     * @param fileParamObject file param object
     *                        //读取下一批文件
     */
    public void readerFile(FileParamObject fileParamObject) {
        log.info("readerFile=====================================================");
        log.info(JSON.toJSONString(fileParamObject));
        FilePath filePath = fileParamObject.getFilePath();
        JSONArray data = fileParamObject.getData();
        int dataLineNum = fileParamObject.getDataLineNum();
        if (dataLineNum == -1) {
            String value = FileUtils.readString(filePath.getRoot(), filePath.getTenantCode(), filePath.getPath());
            JSONObject jsonObject = JSON.parseObject(value);
            fileParamObject.setParam(jsonObject);
            return;
        }
        JSONArray jsonArray = FileUtils.readLines(filePath.getRoot(), filePath.getTenantCode(), filePath.getPath(), dataLineNum);
        data.clear();
        data.addAll(jsonArray);
    }

    /**
     * Write file *
     *
     * @param fileParamObject file param object
     *                        //写入下一批文件
     */
    public void writeFile(FileParamObject fileParamObject) {
        log.info("writeFile=====================================================");
        log.info(JSON.toJSONString(fileParamObject));
        FilePath filePath = fileParamObject.getFilePath();
        JSONArray data = fileParamObject.getData();
        int dataLineNum = fileParamObject.getDataLineNum();
        boolean isAppend = true;
        if (dataLineNum == -1) {
            JSONObject data1 = fileParamObject.getParam();
            isAppend = false;
            FileUtils.writer(filePath.getRoot(), filePath.getTenantCode(), filePath.getPath(), JSON.toJSONString(data1), isAppend);
            return;
        }
        if (dataLineNum == 1) {
            isAppend = false;
        }
        FileUtils.appendLines(filePath.getRoot(), filePath.getTenantCode(), filePath.getPath(), data, isAppend);
        data.clear();
    }

    /**
     * Call interface *
     *
     * @param fileParamObject file param object
     * @param fileParam       file param
     *                        //直接调用接口
     */
    public void callInterface(FileParamObject fileParamObject, FileParam fileParam) {
        sysInterfaceService.callInterfaceForJobDatasourceVo(fileParamObject.getJobDatasourceVo(), fileParam);
    }

    /**
     * Excute string
     *
     * @param str             str
     * @param fileParamObject file param object
     * @return the string
     * 测试  用于写脚本
     */
    public String excute(Object str, FileParamObject fileParamObject) {
        System.out.println("-=====================================================");
        System.out.println(str);
        //读取文件
//        println "开始   -------------------------------"
//        println (A_data_data)
//        println com.alibaba.fastjson.JSON.toJSONString(A_data_data);

        // groovyOutJavaService.callInterface(A_data_data,new FileParam())
        JSONObject data = fileParamObject.getParam();
        FileParam fileParam = JSON.parseObject(data.toJSONString(), FileParam.class);
        JSONArray A_body_data = (JSONArray) JsonObjectUtils.getJsonData(data, "body.data");
        for (Object a_body_datum : A_body_data) {
            BodyPointObject bodyPointObject = JSON.parseObject(JSON.toJSONString(a_body_datum), BodyPointObject.class);
            bodyPointObject.setIsSend(1);
//            bodyPointObject.setIndexLine();
            bodyPointObject.setSleepTime(3000L);
            Object one = getOne(fileParamObject, bodyPointObject);
//            Object one = groovyOutJavaService.getOne(A_data_data, bodyPointObject);
        }
        JSONArray wtsUserBusData = new JSONArray();
        BodyPointObject bodyPointObject = new BodyPointObject();
//        wtsUserBusData.add()
//        fileParamObject.setData();
//        //出参接口赋值
//        fileParamObject.setParam(data);
//        fileParamObject.setData(fileParamObject.getData());
//        writeFile();
//        writeFile();
        return null;

    }


//    public void groovyTest() {
//
//
//        Map<String, Object> vars = new HashMap<>();
//
//
//        vars.put("boData", "F:/工作/test.txt");
//
//
////        String script = "return groovyOutJavaService.excute(boData)";
//        String script = " new File(boData).eachLine {  \n" +
//                "         line -> println \"line : $line\"; \n" +
//                "      }";
//
//
//        Object rtn = groovyScriptEngine.executeObject(script, vars);
//
//
//
//    }
}