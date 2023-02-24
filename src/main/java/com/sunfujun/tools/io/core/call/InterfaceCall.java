package com.sunfujun.tools.io.core.call;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sunfujun.tools.io.core.cahe.LocalCache;
import com.sunfujun.tools.io.core.exception.BizException;
import com.sunfujun.tools.io.core.util.Constants;
import com.sunfujun.tools.io.core.util.JsonUtil;
import com.sunfujun.tools.io.model.entity.SysInterfaceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 接口调用通用类
 *
 * @author scott
 */
@Component
public class InterfaceCall {

    private static final Logger log = LoggerFactory.getLogger(InterfaceCall.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LocalCache localCache;


    /**
     * @param request 接口调用请求类
     * @return 接口调用结果
     * @throws JsonProcessingException
     */
    public InterfaceResult call(InterfaceCallRequest request) {
        List<SysInterfaceLog> interfaceLogs = new ArrayList<>();
        //循环下标
        int index = 0;
        //循环条件
        boolean flag = false;
        do try {
            flag = false;
            index++;
            //接口如果是循环调用接口
            if (request.getIsLoop()) {
                //填充循环调用参数
                initLooParam(request, index - 1);
            }
            //请求类   //单次调用返回
            IRequest iRequest = new IRequest(restTemplate);
            SysInterfaceLog interfaceLog = iRequest.call(request);
            //处理返回结果，是否下次调用
            flag = isNextCall(request, interfaceLogs, interfaceLog);
            //休眠时间
//            Object sleepTime = request.getLooParam().get("sleepTime");
//            if (sleepTime != null && (long) sleepTime != 0L) {
//                Thread.sleep((long) sleepTime);
//            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace(); //处理日志
            return new InterfaceResult(flag, interfaceLogs);
        } while (flag && index < 10000);//最多调用万次

        return new InterfaceResult(true, interfaceLogs);

    }

    //是否要下一次调用
    private boolean isNextCall(InterfaceCallRequest request, List<SysInterfaceLog> interfaceLogs, SysInterfaceLog interfaceLog) {
        //获取每页数量
        boolean isNext = true;
        if (!interfaceLog.getIsSuccess()) { //如果调用失败
            return false;
        }
        //缓存
        InterfaceCallRequest requestLog = JsonUtil.parse(interfaceLog.getRequestJson(), InterfaceCallRequest.class);
        if (requestLog != null) {
            //接口返回缓存参数维护进内存
            if (!request.getCache().isEmpty()) {
                for (CacheParam cache : request.getCache()) {
                    String cacheValue = JsonUtil.getJsonNodeValue(interfaceLog.getResponseBody(), String.valueOf(cache.getValue()));
                    if (StrUtil.isNotEmpty(cacheValue)) {
                        localCache.put(request.getId(), cache.getName(), cacheValue, cache.getTime());
                    }
                }
            }
        }
        String responseBody = interfaceLog.getResponseBody();
        if (request.getIsLoop()) {
            long pageSize = (long) request.getLooParam().get("pageSizeValue");
            //获取循环调用返回体节点，进while判断下次是否调用
            String loopReturnNode = (String) request.getLooParam().get("loopReturn");
            JsonNode node = JsonUtil.findJsonNode(responseBody, loopReturnNode);
            if (node == null || !node.isArray()) {
                throw new BizException("维护循环调用返回节点不正确！");
            }
            //如果没有下一页了 ，不继续循环
            if (node.size() < pageSize) {
                isNext = false;
            }
        } else {
            isNext = false;
        }
        //是否开启只执行一次
        if (request.getIsOne()) {
            isNext = false;
        }

        //添加接口调用日志
//            boolean saveFlag = interfaceLogService.addInterfaceCallLog(interfaceLog);
//            if (saveFlag){
//                log.info("保存接口调用日志成功,interfaceId:"+interfaceLog.getInterfaceId());
//            }else {
//                log.error("保存接口调用日志失败,interfaceId:"+interfaceLog.getInterfaceId());
//            }

        interfaceLogs.add(interfaceLog);
        return isNext;
    }

//        开线程调用
//        }


//        int size = list.size();
//        if (size>Constants.THREAD_PAGE_NUM){
//            List<List<?>> split =  CommonUtil.averageAssign(list, Constants.THREAD_PAGE_NUM);
//            int threadNum = split.size();·
//            //手动创建线程池
//            executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
//            for (List<?> objects : split) {
//                IRequest request = new IRequest((List<InterfaceCallRequest>) objects, restTemplate);
//                Future<List<SysInterfaceLog>> future = executorService.submit(request);

//                futureList.add(future);
//            }
//
//        }else {
//            executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
//            Future<List<SysInterfaceLog>> future = executorService.submit(executorService);
//            futureList.add(future);
//        }
//
//        if (!futureList.isEmpty()){
//            for (Future<List<SysInterfaceLog>> future: futureList){
//                interfaceLogs.addAll(future.get());
//            }
//        }

//        for(SysInterfaceLog interfaceLog: interfaceLogs){
//            if (interfaceLog.getIsSuccess()){
//                InterfaceCallRequest request = JsonUtil.parse(interfaceLog.getRequestJson(), InterfaceCallRequest.class);
//                assert request != null;
//                if (!request.getCache().isEmpty()){
//                    for (CacheParam cache : request.getCache()) {
//                        String cacheValue = JsonUtil.getJsonNodeValue(interfaceLog.getResponseBody(), String.valueOf(cache.getValue()));
//                        if (StrUtil.isNotEmpty(cacheValue)){
//                            interfaceLocalCache.put(request.getId(), cache.getName(), cacheValue, cache.getTime());
//                        }
//                    }
//                }
//            }


//    private long initLooParamValue(InterfaceCallRequest request, InterfaceLoopParamDetail paramDetail) throws JsonProcessingException {
//
//        Map<String, Object> headerMap = request.getHeader();
//        Map<String, Object> queryMap = request.getQuery();
//        String value = paramDetail.getValue();
//        if (StrUtil.isEmpty(value)) {
//            if (Constants.REQUEST_HEADER.equals(paramDetail.getCate())) {
//                value = String.valueOf(headerMap.get(paramDetail.getNode()));
//            } else if (Constants.REQUEST_QUERY.equals(paramDetail.getCate())) {
//                value = String.valueOf(queryMap.get(paramDetail.getNode()));
//            } else if (Constants.REQUEST_BODY.equals(paramDetail.getCate())) {
//                value = JsonUtil.getJsonNodeValue(request.getBody(), paramDetail.getNode());
//            }
//        }
//        if (!StrUtil.isEmpty(value)) {
//            return Long.parseLong(value);
//        }
//        return 0L;
//    }


    /**
     * 初始化循环参数
     *
     * @param request 通用请求类
     * @param i       调用下标
     */
    private void initLooParam(InterfaceCallRequest request, int i) {

        Map<String, Object> loopMap = request.getLooParam();

        if (i > 0) {
            String currentPage = (String) loopMap.get("currentPage");
            String[] currentPageArray = currentPage.split("_");
            String currentPageCate = currentPageArray[0];
            String currentPageNode = currentPageArray[1];
            long res = loopMap.containsKey("currentPageValue") ? (long) loopMap.get("currentPageValue") : -1L;
            if (Constants.REQUEST_HEADER.equals(currentPageCate)) {
                if (res == -1L) {
                    res = Long.parseLong(String.valueOf(request.getHeader().get(currentPageNode)));
                    loopMap.put("currentPageValue", res);
                }
                request.getHeader().put(currentPageNode, res + i);
            } else if (Constants.REQUEST_QUERY.equals(currentPageCate)) {
                if (res == -1L) {
                    res = Long.parseLong(String.valueOf(request.getQuery().get(currentPageNode)));
                    loopMap.put("currentPageValue", res);
                }
                request.getQuery().put(currentPageNode, res + i);
            } else if (Constants.REQUEST_BODY.equals(currentPageCate)) {
                if (res == -1L) {
                    res = Long.parseLong(JsonUtil.getJsonNodeValue(request.getBody(), currentPageNode));
                    loopMap.put("currentPageValue", res);
                }
                request.setBody(JsonUtil.replaceJsonNodeValue(request.getBody(), currentPageNode, String.valueOf(res + i)));
            }
        }

        //第一次进入或者pageSizeValue不存在
        if (i == 0 || !loopMap.containsKey("pageSizeValue")) {
            String pageSize = (String) loopMap.get("pageSize");
            String[] pageSizeArray = pageSize.split("_");
            String pageSizeCate = pageSizeArray[0];
            String pageSizeNode = pageSizeArray[1];
            //默认
            long pageSizeValue = 10L;
            if (Constants.REQUEST_HEADER.equals(pageSizeCate)) {
                pageSizeValue = Long.parseLong(String.valueOf(request.getHeader().get(pageSizeNode)));
            } else if (Constants.REQUEST_QUERY.equals(pageSizeCate)) {
                pageSizeValue = Long.parseLong(String.valueOf(request.getQuery().get(pageSizeNode)));
            } else if (Constants.REQUEST_BODY.equals(pageSizeCate)) {
                pageSizeValue = Long.parseLong(JsonUtil.getJsonNodeValue(request.getBody(), pageSizeNode));
            }
            loopMap.put("pageSizeValue", pageSizeValue);
        }
        request.setLooParam(loopMap);
    }
}
