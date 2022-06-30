package cn.com.coho.tools.io.core.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class JsonObjectUtils {

    //获取json数据的固定指向data
    //并且替换成指向引用内容
    public static Object getJsonData(JSONObject json, String pointData) {
        if (pointData == null || "".equals(pointData)) {
            throw new RuntimeException("获取json数据出错：".concat(pointData));
        }
        String[] split = StringUtils.split(pointData, ".");
        Object dataParent = json;
        Object data = json;
        for (String key : split) {
            dataParent = data;
            if (dataParent == null || !(dataParent instanceof JSONObject)) {
                throw new RuntimeException("获取json数据出错：".concat(pointData));
            }
            JSONObject parent = (JSONObject) dataParent;
            data = parent.get(key);
        }
        return data;
    }

    //获取json数据的固定指向data
    //并且替换成指向引用内容
    public static Object setJsonData(JSONObject json, String pointData, Object value) {
        if (pointData == null || "".equals(pointData)) {
            throw new RuntimeException("获取json数据出错：".concat(pointData));
        }
        String[] split = StringUtils.split(pointData, ".");
        Object dataParent = json;
        Object data = json;
        String keyThis = null;
        for (String key : split) {
            keyThis = key;
            dataParent = data;
            if (dataParent == null || !(dataParent instanceof JSONObject)) {
                throw new RuntimeException("获取json数据出错：".concat(pointData));
            }
            JSONObject parent = (JSONObject) dataParent;
            data = parent.get(key);
        }
        JSONObject dataParent1 = (JSONObject) dataParent;
        dataParent1.put(keyThis, value);
        return data;
    }
}
