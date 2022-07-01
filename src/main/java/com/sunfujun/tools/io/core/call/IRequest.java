package com.sunfujun.tools.io.core.call;

import cn.hutool.core.util.StrUtil;
import com.sunfujun.tools.io.core.util.Constants;
import com.sunfujun.tools.io.core.util.JsonUtil;
import com.sunfujun.tools.io.model.entity.SysInterfaceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 接口调用
 */
public class IRequest implements Callable<List<SysInterfaceLog>> {

    private static final Logger log = LoggerFactory.getLogger(IRequest.class);

    private RestTemplate restTemplate;

    private List<InterfaceCallRequest> interfaceCallRequests;


    public IRequest(List<InterfaceCallRequest> requests, RestTemplate restTemplate) {
        this.interfaceCallRequests = requests;
        this.restTemplate = restTemplate;
    }

    public IRequest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //实现多线程方法，暂时没使用
    @Override
    public List<SysInterfaceLog> call() throws Exception {
        List<SysInterfaceLog> list = new ArrayList<>();
        for (InterfaceCallRequest request : interfaceCallRequests) {
            SysInterfaceLog result = this.call(request);
            list.add(result);
        }
        return list;
    }


    //接口调用返回接口实体
    public SysInterfaceLog call(InterfaceCallRequest request) {
        log.info("接口：{}生成的请求：{}", request.getName(), JsonUtil.toJSONString(request));

        HttpHeaders headers = new HttpHeaders();
        if (!request.getHeader().isEmpty()) {
            for (Map.Entry<String, Object> entry : request.getHeader().entrySet()) {
                headers.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        StringBuilder sb = null;
        if (!request.getQuery().isEmpty()) {
            for (Map.Entry<String, Object> entry : request.getQuery().entrySet()) {
                if (sb == null) {
                    sb = new StringBuilder("?");
                }
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        String url = sb != null ? request.getUrl() + sb : request.getUrl();

        log.info("url:{}", url);

        HttpEntity<String> entity = request.getBody() != null ? new HttpEntity<>(request.getBody(), headers) : new HttpEntity<>(headers);


        Long beginTime = System.currentTimeMillis();

        ResponseEntity<String> responseEntity = request.getType() == 0 ? this.doGet(url, entity) : this.doPost(url, entity);

        Long endTime = System.currentTimeMillis();

        // 获取状态码
        int statusCode = responseEntity.getStatusCodeValue();
        // 获取headers
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        // 获取body
        String body;
        if (responseEntity.getBody() != null && responseEntity.getBody().getBytes(StandardCharsets.UTF_8).length > Constants.LENGTH_1M) {
            body = "返回体超过1M,请重新维护参数调用!";
        } else {
            body = responseEntity.getBody();
        }
        log.info("接口:{}返回：{}", request.getName(), body);

        String keyName = request.getFlag().keySet().iterator().next();

        String value = JsonUtil.getJsonNodeValue(body, keyName);

        boolean isSuccess = StrUtil.isNotEmpty(value) && value.equals(request.getFlag().get(keyName));

        return new SysInterfaceLog(request.getId(), JsonUtil.toJSONString(request), JsonUtil.toJSONString(httpHeaders), body, statusCode, new Date(), endTime - beginTime, isSuccess);
    }


    //get 请求 restTemplate 调用
    public ResponseEntity<String> doGet(String url, HttpEntity<String> entity) {
        return restTemplate.getForEntity(url, String.class, entity);
    }


    //post 请求 restTemplate 调用
    public ResponseEntity<String> doPost(String url, HttpEntity<String> entity) {
        return restTemplate.postForEntity(url, entity, String.class);
    }


}
