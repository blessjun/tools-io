package cn.com.coho.tools.io.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonUtil {


    public static List<List<?>> averageAssign(List<?> list, int len){
        if (list == null || list.isEmpty() || len < 1) {
            return Collections.emptyList();
        }
        int size = list.size();
        List<List<?>> result = new ArrayList<>();
        if (size<=len){
            result.add(list);
            return result;
        }
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<?> subList = list.subList(i * len, (Math.min((i + 1) * len, size)));
            result.add(subList);
        }
        return result;
    }
}
