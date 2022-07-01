package com.sunfujun.tools.io.core.cahe;


import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.sunfujun.tools.io.core.util.Constants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author scott
 * 本地缓存类
 */
@Component
public class LocalCache {

    private final TimedCache<String, Object> timedCache = CacheUtil.newTimedCache(0);

    {
        timedCache.schedulePrune(300 * Constants.TIME_S);
    }


    //生成接口缓存key
    private static String genKey(Long interfaceId, String param){
        return interfaceId+"_return_"+param;
    }


    public void put(Long interfaceId, String paramName, String value, Long timeOut){
       timedCache.put(genKey(interfaceId, paramName), value, timeOut*1000);
    }

    public void put(String key, Object value, Long timeOut){
        timedCache.put(key, value, timeOut*1000);
    }

    public void clear(){
        timedCache.clear();
    }

    public boolean containsKey(Long interfaceId, String paramName){
        return timedCache.containsKey(genKey(interfaceId, paramName));
    }

    public Object getCacheParam(Long interfaceId, String paramName){
        return this.get(genKey(interfaceId, paramName));
    }

    public Object get(String key){
        return timedCache.get(key, false);
    }


    //查看当前缓存列表
    public Map<String, Object> cacheList(){
        Map<String, Object> map = new HashMap(2^2);
        if(!timedCache.isEmpty()){
            Set<String> set = timedCache.keySet();
            set.forEach(s -> {
                map.put(s, timedCache.get(s));
            });
        }
        return map;
    }
}
