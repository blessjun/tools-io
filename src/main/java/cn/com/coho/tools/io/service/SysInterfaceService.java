package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.core.cahe.LocalCache;
import cn.com.coho.tools.io.core.call.CacheParam;
import cn.com.coho.tools.io.core.call.InterfaceCall;
import cn.com.coho.tools.io.core.call.InterfaceCallRequest;
import cn.com.coho.tools.io.core.call.InterfaceResult;
import cn.com.coho.tools.io.core.call.file.*;
import cn.com.coho.tools.io.core.exception.BizException;
import cn.com.coho.tools.io.core.groovy.GroovyScriptEngine;
import cn.com.coho.tools.io.core.util.*;
import cn.com.coho.tools.io.model.dto.*;
import cn.com.coho.tools.io.model.entity.*;
import cn.com.coho.tools.io.model.vo.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author scott
 */
@Service
public class SysInterfaceService {

    private static final Logger log = LoggerFactory.getLogger(SysInterfaceService.class);

    @Autowired
    private InterfaceCall interfaceCall;

    @Autowired
    private SysInterfaceInfoService interfaceInfoService;

    @Autowired
    private SysInterfaceGroupService sysInterfaceGroupService;

    @Autowired
    private SysInterfaceLogService sysInterfaceLogService;

    @Autowired
    private SysParamService sysParamService;

    @Autowired
    private LocalCache localCache;

    @Autowired
    private SysInterfaceParamService interfaceParamService;

    @Autowired
    private GroovyScriptEngine groovyScriptEngine;


    /**
     * 保存接口信息
     * @param dto
     * @return
     * @throws Exception
     */
    public Result<Long> save(InterfaceDto dto) throws Exception {

        if (dto == null || ArrayUtil.isEmpty(dto.getParams())) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }

        SysInterfaceGroup group = sysInterfaceGroupService.getById(dto.getGroupId());
        if (group == null) {
            return Result.newInstance(ResponseCode.GROUP_NOT_FOUND);
        }

        List<SysInterfaceInfo> groupInterfaces = interfaceInfoService.list(new LambdaQueryWrapper<SysInterfaceInfo>().eq(SysInterfaceInfo::getGroupId, dto.getGroupId()));

        if (!groupInterfaces.isEmpty()) {
            boolean present = groupInterfaces.stream().anyMatch(m -> m.getCode().equals(dto.getCode()) && !m.getId().equals(dto.getId()));
            if (present) {
                return Result.newInstance(ResponseCode.CODE_REPEAT);
            }
            present = groupInterfaces.stream().anyMatch(m -> m.getName().equals(dto.getName()) && !m.getId().equals(dto.getId()));
            if (present) {
                return Result.newInstance(ResponseCode.NAME_REPEAT);
            }
        }
        SysInterfaceInfo info = interfaceInfoService.getById(dto.getId());

        if (info == null) {
            info = new SysInterfaceInfo();
        }

        BeanUtil.copyProperties(dto, info);

        info.setGroupName(group.getName());

        if (dto.getIsLoop()) {
            if (dto.getLoopParams() == null
                    || dto.getLoopParams().getPageTotal() == null
                    || dto.getLoopParams().getPageNo() == null
                    || dto.getLoopParams().getPageSize() == null) {
                return Result.newInstance(ResponseCode.PARAM_FAIL);
            }
            info.setLoopParam(JsonUtil.toJSONString(dto.getLoopParams()));
        } else {
            info.setLoopParam(null);
        }

        List<SysInterfaceParam> interfaceParams = BeanConvertUtils.convertToList(dto.getParams(), SysInterfaceParam::new);

//        info.setIsOne(true);
//
//        InterfaceResult result = interfaceCall.call(initRequest(info, interfaceParams));
//
//        info.setStatus(result.getIsSuccess());
//
//        if (!) {
//            return Result.newInstance(ResponseCode.SAVE_FAIL);
//        }
        Long id = interfaceInfoService.addOrUpd(info, interfaceParams) ? info.getId() : 0L;
        return Result.buildSuccess(info.getId());
    }


    /**
     * 接口调用
     * @param dto
     * @return
     * @throws Exception
     */
    public Result<InterfaceResult> call(InterfaceDto dto) throws Exception {
        SysInterfaceInfo info = interfaceInfoService.getById(dto.getId());
        if (info == null) {
            info = new SysInterfaceInfo();
        }
        BeanUtil.copyProperties(dto, info);
        if (dto.getIsLoop()) {
            if (dto.getLoopParams() == null
                    || dto.getLoopParams().getPageTotal() == null
                    || dto.getLoopParams().getPageNo() == null
                    || dto.getLoopParams().getPageSize() == null) {
                return Result.newInstance(ResponseCode.PARAM_FAIL);
            }
            info.setLoopParam(JsonUtil.toJSONString(dto.getLoopParams()));
        } else {
            info.setLoopParam(null);
        }

        List<SysInterfaceParam> interfaceParams = BeanConvertUtils.convertToList(dto.getParams(), SysInterfaceParam::new);

//        info.setIsOne(dto.getIsOne());

        return Result.buildSuccess(interfaceCall.call(initRequest(info, interfaceParams)));
    }

    //删除接口
    public Result<Boolean> del(List<Long> ids) {
        return interfaceInfoService.del(ids);
    }


    //查询接口详情
    public Result<InterfaceInfoVo> query(Long id) {
        return interfaceInfoService.query(id);
    }


    //获取接口列表
    public Result<List<SysInterfaceInfo>> list(InterfaceSearchDto dto) {
        if (dto == null || dto.getPageIndex() == null || dto.getPageSize() == null) {
            return Result.buildSuccess(interfaceInfoService.list());
        }
        return interfaceInfoService.listResult(dto);
    }


    //接口状态更新
    public Result<Boolean> stateUpd(StateUpdDto dto) {
        if (ArrayUtil.isEmpty(dto.getIds()) || dto.getIsUse() == null) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        return Result.buildSuccess(interfaceInfoService.update(new LambdaUpdateWrapper<SysInterfaceInfo>().set(SysInterfaceInfo::getIsUse, dto.getIsUse()).in(SysInterfaceInfo::getId, dto.getIds())));
    }


    //接口分组信息
    public Result<List<SysInterfaceGroup>> groupList() {
        return Result.buildSuccess(sysInterfaceGroupService.list());
    }

    //新增或编辑接口分组
    public Result<Boolean> addOrUpdGroup(InterfaceGroupDto dto) {
        if (dto == null || StrUtil.isEmpty(dto.getName())) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        SysInterfaceGroup sysInterfaceGroup = sysInterfaceGroupService.getOne(new LambdaQueryWrapper<SysInterfaceGroup>().eq(SysInterfaceGroup::getName, dto.getName()));
        if (sysInterfaceGroup == null) {
            sysInterfaceGroup = new SysInterfaceGroup();
            BeanUtil.copyProperties(dto, sysInterfaceGroup);
        } else {
            if (!sysInterfaceGroup.getId().equals(dto.getId())) {
                return Result.newInstance(ResponseCode.NAME_REPEAT, false);
            }
            sysInterfaceGroup.setName(dto.getName());
        }
        return Result.buildSuccess(sysInterfaceGroupService.saveOrUpdate(sysInterfaceGroup));
    }


    //接口下拉框
    public Result<List<SimpleVo>> listBox() {
        return Result.buildSuccess(interfaceInfoService.findByIsExistCacheParam());
    }


    //查询接口调用日志记录
    public Result<List<SysInterfaceLog>> interfaceCallRecords(IdDto dto) {
        if (dto == null || dto.getId() == null || dto.getId() == 0L) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        return Result.buildSuccess(sysInterfaceLogService.interfaceFiveLogs(dto.getId()));
    }


    //根据接口id获取通用request
    public InterfaceCallRequest getRequestInfoByInterfaceId(Long interfaceId) throws Exception {

        SysInterfaceInfo info = interfaceInfoService.getById(interfaceId);
        if (info == null) {
            throw new BizException("查不到接口信息,interfaceId:" + interfaceId);
        }
        List<SysInterfaceParam> params = interfaceParamService.getInfoByInterfaceId(interfaceId);

        return this.initRequest(info, params);
    }


//    public InterfaceCallRequest initRequest(Long interfaceId, )


    /**
     * 生成接口请求通用
     *
     * @param info            接口信息
     * @param interfaceParams 接口参数信息
     * @return InterfaceCallRequest 接口调用通用请求
     * @throws JsonProcessingException json异常
     */
    public InterfaceCallRequest initRequest(SysInterfaceInfo info, List<SysInterfaceParam> interfaceParams) throws Exception {

        log.info("call interface info:{}, param:{}", JsonUtil.toJSONString(info), JsonUtil.toJSONString(interfaceParams));

        Optional<SysInterfaceParam> flagParam = interfaceParams.stream().filter(r -> ParamTypeEnum.FLAG.getType().equals(r.getType()) && Constants.REQUEST_RETURN.equals(r.getCategory())).findFirst();

        if (!flagParam.isPresent()) {
            throw new BizException(300, "接口无成功标识参数");
        }

        Map<String, Object> flag = new HashMap<>();

        flag.put(flagParam.get().getName(), flagParam.get().getValue());

        Map<String, Object> headerMap = new HashMap<>();

        Map<String, Object> queryMap = new HashMap<>();

        Map<String, Object> paraMap = new HashMap<>();

        for (SysInterfaceParam param : interfaceParams) {
            Object realValue = convertValue(param.getType(), param.getValue(), param.getObjectId());
            if (Constants.REQUEST_HEADER.equals(param.getCategory())) {
                headerMap.put(param.getName(), String.valueOf(realValue));
            } else if (Constants.REQUEST_QUERY.equals(param.getCategory())) {
                queryMap.put(param.getName(), String.valueOf(realValue));
            }
            paraMap.put(param.getCategory() + "_" + param.getName(), realValue);
        }

        String bodyParam = null;

        List<SysInterfaceParam> bodyParams = interfaceParams.stream().filter(r -> Constants.REQUEST_BODY.equals(r.getCategory())).collect(Collectors.toList());

        if (!bodyParams.isEmpty()) {
            bodyParam = bodyParams.get(0).getValue();
            for (Map.Entry<String, Object> m : paraMap.entrySet()) {
                String value = "@" + m.getKey();
                if (bodyParam.contains(value)) {
                    bodyParam = bodyParam.replaceAll(value, String.valueOf(m.getValue()));
                }
                ;
            }
            if (bodyParam.contains("@all_")) {
                List<SysParam> sysParams = sysParamService.list(new LambdaQueryWrapper<SysParam>().eq(SysParam::getGroupId, info.getGroupId()));
                if (!sysParams.isEmpty()) {
                    for (SysParam param : sysParams) {
                        String value = "@all_" + param.getName();
                        if (bodyParam.contains(value)) {
                            bodyParam = bodyParam.replaceAll(value, String.valueOf(convertValue(param.getType(), param.getValue(), param.getObjectId())));
                        }
                    }
                }
            }
        }
        //签名
        List<SysInterfaceParam> sysInterfaceParams = interfaceParams.stream().filter(r -> ParamTypeEnum.SIGN.getType().equals(r.getType())).collect(Collectors.toList());

        if (!sysInterfaceParams.isEmpty()) {
            SysInterfaceParam signParam = sysInterfaceParams.get(0);
            String sign = HttpClientUtil.generateSign(signParam.getValue(), paraMap);
            log.info("生成的签名：{}", sign);
            sysInterfaceParams.forEach(sysInterfaceParam -> {
                if (Constants.REQUEST_HEADER.equals(sysInterfaceParam.getCategory())) {
                    headerMap.put(signParam.getName(), sign);
                } else if (Constants.REQUEST_QUERY.equals(sysInterfaceParam.getCategory())) {
                    queryMap.put(signParam.getName(), sign);
                }
            });
        }

        //查询缓存参数
        List<SysInterfaceParam> cacheParams = interfaceParams.stream().filter(param -> ParamTypeEnum.CACHE.getType().equals(param.getType())).collect(Collectors.toList());

        List<CacheParam> cache = BeanConvertUtils.convertToList(cacheParams, CacheParam::new);

        InterfaceCallRequest request = new InterfaceCallRequest(info.getId(), info.getName(), info.getPath(), info.getType(), info.getIsOne() != null && info.getIsOne(), info.getIsLoop(), headerMap, queryMap, bodyParam, flag, cache);
        if (info.getIsLoop()) {
            InterfaceLoopParams loopParams = JsonUtil.parse(info.getLoopParam(), InterfaceLoopParams.class);
            if (loopParams == null) {
                throw new BizException(300, "循环调用接口无调用参数！");
            }
            this.initLooParam(request, loopParams);
        }
        return request;
    }


    /**
     * 根据参数类型换算参数真实值
     * @param type 参数类型
     * @param value 参数值
     * @param objectId 对象id-接口id
     * @return
     * @throws Exception
     */
    public Object convertValue(Integer type, String value, Long objectId) throws Exception {
        ParamTypeEnum typeEnum = ParamTypeEnum.get(type);
        switch (typeEnum) {
            case BOOLEAN:
                try {
                    return Boolean.parseBoolean(value);
                } catch (Exception e) {
                    throw new BizException("boolean类型参数转化异常");
                }
            case INTEGER:
                try {
                    return Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    throw new BizException("Integer类型参数转化异常");
                }
            case DATE:
                try {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
                } catch (ParseException e) {
                    throw new BizException("日期参数转化异常");
                }
            case LONG:
                try {
                    return Long.valueOf(value);
                } catch (NumberFormatException e) {
                    throw new BizException("Long类型参数转化异常");
                }
            case TIMESTAMP:
                try {
                    long timestamp = Instant.now().toEpochMilli();
                    if ("10".equals(value)) {
                        timestamp = timestamp / 1000;
                    }
                    return timestamp;
                } catch (Exception e) {
                    throw new BizException("时间戳参数转化异常");
                }
            case INTERFACE:
                //如果是接口类型，则取缓存中查询，查询不到调用接口。
                if (!localCache.containsKey(objectId, value)) {
                    if (!interfaceCall.call(getRequestInfoByInterfaceId(objectId)).getIsSuccess()) {
                        throw new BizException("接口参数转化异常:接口:" + objectId + "调用失败！");
                    }
                }
                Object cacheValue = localCache.getCacheParam(objectId, value);
                if (cacheValue == null) {
                    throw new BizException("接口参数转化异常: 接口" + objectId + "的缓存参数:" + value + "赋值失败!");
                }
                return cacheValue;
            case RULE:
                //如果是规则类型，则执行规则引擎，获取返回值
                Object ruleValue = groovyScriptEngine.executeObject(value, null);
                if (ruleValue == null) {
                    throw new BizException("规则参数赋值异常!");
                }
                return ruleValue;
            default:
                return value;
        }
    }


    //初始化循环参数，存入通用request请求
    private void initLooParam(InterfaceCallRequest request, InterfaceLoopParams loopParams) {
        try {

            log.info("循环调用数据loopParams:{}", JsonUtil.toJSONString(loopParams));

            Map<String, Object> looParaMap = new HashMap<>();
            looParaMap.put("loopReturn", loopParams.getPageTotal().getNode());

            looParaMap.put("pageSize", loopParams.getPageSize().getCate() + "_" + loopParams.getPageSize().getNode());

            looParaMap.put("currentPage", loopParams.getPageNo().getCate() + "_" + loopParams.getPageNo().getNode());

            if (loopParams.getPageSize().getValue() != null) {
                looParaMap.put("pageSizeValue", Long.parseLong(loopParams.getPageSize().getValue()));
            }

            if (loopParams.getPageNo().getValue() != null) {
                looParaMap.put("currentPageValue", Long.parseLong(loopParams.getPageNo().getValue()));

            }
            request.setLooParam(looParaMap);

        } catch (Exception e) {
            log.error("循环调用参数赋值异常", e);
            throw new BizException("循环调用参数赋值异常！", e);
        }
    }

    //根据数据源调用接口保存到文件
    public void callInterfaceToFile(JobDatasourceVo jobDatasourceVo, JobInfoVo jobInfoVo) {
        //校验id
        Long pointObjectId = jobDatasourceVo.getPointObjectId();
        if (pointObjectId == null) {
            throw new BizException("数据源指向接口有误".concat(JSON.toJSONString(jobDatasourceVo)));
        }
        InterfaceResult call;
        try {
            //调用接口
            InterfaceCallRequest requestInfoByInterfaceId = getRequestInfoByInterfaceId(pointObjectId);
            requestInfoByInterfaceId.setDescribe(jobDatasourceVo.getJobCode().concat("任务："));
            call = interfaceCall.call(requestInfoByInterfaceId);
        } catch (Exception e) {
            log.error("接口调用错误:".concat(JSON.toJSONString(jobDatasourceVo)), e);
            throw new BizException("接口调用错误:".concat(JSON.toJSONString(jobDatasourceVo)), e);
        }
        if (!call.getIsSuccess()) {
            throw new BizException("接口参数返回错误:".concat(JSON.toJSONString(jobDatasourceVo)));
        }
        //写入文件
        List<SysInterfaceLog> logList = call.getLogList();
        FilePath filePathParam = JobFilePathManage.getPathParam(jobDatasourceVo, jobInfoVo);
        FilePath filePathBody = JobFilePathManage.getPathBody(jobDatasourceVo, jobInfoVo);
        FilePath filePathDir = JobFilePathManage.getPathDir(jobDatasourceVo, jobInfoVo);
        //清理文件
        FileUtils.clean(filePathDir.getRoot(), filePathDir.getTenantCode(), filePathDir.getPath());
        FileParam fileParam = new FileParam();
        for (SysInterfaceLog sysInterfaceLog : logList) {
            //写入指向文件body内容
            writerPointContent(fileParam, sysInterfaceLog, filePathBody, jobDatasourceVo);
        }
        //写入指向文件参数内容
        FileUtils.writer(filePathParam.getRoot(), filePathParam.getTenantCode(), filePathParam.getPath(), JSON.toJSONString(fileParam), false);

    }

    //接口调用写入指向文件body内容
    public void writerPointContent(FileParam fileParam, SysInterfaceLog sysInterfaceLog, FilePath filePathBody, JobDatasourceVo jobDatasourceVo) {
        Map<String, Object> header = fileParam.getHeader();
        String responseHeader = sysInterfaceLog.getResponseHeader();
        if (responseHeader != null && !"".equals(responseHeader)) {
            header.putAll(JSON.parseObject(responseHeader, Map.class));
        }
        //获取返回体
        Object body = fileParam.getBody();
        String responseBody = sysInterfaceLog.getResponseBody();
        Object bodyJson = JSON.parse(responseBody);
        //指向具体内容
        String pointObjectData = jobDatasourceVo.getPointObjectData();
        String[] split = StringUtils.split(pointObjectData, ",");
        //初始化
        initBodyPointData(fileParam, pointObjectData, bodyJson);
        //替换指向内容行号
        for (String pointData : split) {
            //替换指向内容行号到参数body 数据单独存放到文件
            List<Object> datalist = replacePointData(fileParam, pointData, bodyJson);
            //写入文件
            if (datalist.size() > 0) {
                String pointPath = filePathBody.getPath().replace(JobFilePathManage.POINT_DATA, pointData);
                FileUtils.appendLines(filePathBody.getRoot(), filePathBody.getTenantCode(), pointPath, datalist, true);
            }

        }
    }

    //初始化替换指向内容成空数组
    public void initBodyPointData(FileParam fileParam, String pointObjectDatas, Object bodyJson) {
        //上次循环调用的body
        Object body = fileParam.getBody();
        if (body != null) {
            return;
        }
        //如果指向body
        if ("body".equalsIgnoreCase(pointObjectDatas)) {
            fileParam.setBody(new JSONArray());
            return;
        }
        JSONObject jsonBody = JSON.parseObject(JSON.toJSONString(bodyJson));
        fileParam.setBody(jsonBody);
        //替换成空数组
        String[] splitPointObjectDatas = StringUtils.split(pointObjectDatas, ",");
        for (String pointObjectData : splitPointObjectDatas) {
            JsonObjectUtils.setJsonData(jsonBody, pointObjectData, new JSONArray());

        }
    }

    //替换指向内容行号到参数body 数据单独存放到文件
    public List<Object> replacePointData(FileParam fileParam, String pointData, Object bodyJson) {
        Object body = fileParam.getBody();
        //如果指向body
        List<Object> replaceOldlist = null;
        Object data = null;
        if ("body".equalsIgnoreCase(pointData)) {
            replaceOldlist = (JSONArray) body;
            data = bodyJson;
        } else {
            replaceOldlist = (JSONArray) JsonObjectUtils.getJsonData((JSONObject) body, pointData);
            data = JsonObjectUtils.getJsonData((JSONObject) bodyJson, pointData);
        }
        //数据转换成list
        List<Object> datalist = new ArrayList<>();
        if (data != null) {
            if (data instanceof JSONArray) {
                datalist.addAll((JSONArray) data);
            } else {
                datalist.add(data);
            }
        }
        //返回数据  增加数据行对象
        int size = replaceOldlist.size();
        for (int i = 0; i < datalist.size(); i++) {
            int index = size + 1 + i;
            BodyPointObject bodyPointObject = new BodyPointObject();
            bodyPointObject.setIndexLine(index);
            replaceOldlist.add(bodyPointObject);
        }
        return datalist;
    }

    //读取文件 替换指向内容行号到参数body
    public boolean replaceReaderPointData(FileParam fileParam, String pointData, Object bodyJson, FilePath filePathBody, BodyPointObject bodyPointObjectIn) {
        String pointPath = filePathBody.getPath().replace(JobFilePathManage.POINT_DATA, pointData);
        Object body = fileParam.getBody();
        //如果指向body 数据查询
        List<Object> replaceOldlist = null;
        Object pointdata = null;
        if ("body".equalsIgnoreCase(pointData)) {
            replaceOldlist = (JSONArray) body;
            pointdata = bodyJson;
        } else {
            replaceOldlist = (JSONArray) JsonObjectUtils.getJsonData((JSONObject) body, pointData);
            pointdata = JsonObjectUtils.getJsonData((JSONObject) bodyJson, pointData);
        }
        //查找数据
        if (pointdata != null && pointdata instanceof JSONArray) {
            JSONArray data = (JSONArray) pointdata;
            //为空代表替换完成了
            if (data.size() == 0) {
                return true;
            }
            //
            int index = 0;
            int preIndex = 0;
            //开始逐行替换
            JSONArray readerArray = new JSONArray();
            replaceOldlist.clear();
            for (Object datum : data) {
                BodyPointObject bodyPointObject = JSON.parseObject(JSON.toJSONString(datum), BodyPointObject.class);
                Integer indexLine = bodyPointObject.getIndexLine();
                if (index - preIndex > readerArray.size() - 1) {
                    readerArray = FileUtils.readLines(filePathBody.getRoot(), filePathBody.getTenantCode(), pointPath, indexLine);
                    if (readerArray.size() == 0) {
                        throw new BizException("数据行数不存在:".concat(JSON.toJSONString(filePathBody)).concat(JSON.toJSONString(bodyPointObject)));
                    }
                    preIndex = index;
                }
                //替换到数据体
                Object obj = readerArray.get(index - preIndex);
                replaceOldlist.add(obj);
                index++;
                if (bodyPointObject.getIsSend() == 1) {
                    bodyPointObjectIn.setSleepTime(bodyPointObject.getSleepTime());
                    break;
                }
            }
            //清理
            for (int i = index - 1; i >= 0; i--) {
                data.remove(i);
            }
            if (data.size() > 0) {
                return false;
            }
        }
        return true;
    }

    //封装脚本groovy 参数
    public Map<String, Object> readFileByJobDatasourceVo(JobDatasourceVo jobDatasourceVo, JobInfoVo jobInfoVo) {
        Map<String, Object> map = new HashMap<>();
        //全部标识参数
        FilePath filePathParam = JobFilePathManage.getPathParam(jobDatasourceVo, jobInfoVo);
        FileParamObject fileParamObject = new FileParamObject();
        fileParamObject.setDataLineNum(-1);
        fileParamObject.setFilePath(filePathParam);
        fileParamObject.setJobDatasourceVo(jobDatasourceVo);
        map.put(JobFilePathManage.getParamKey(jobDatasourceVo), fileParamObject);
        //写入文件 当前文件没内容
        FileUtils.writer(filePathParam.getRoot(), filePathParam.getTenantCode(), filePathParam.getPath(), JSON.toJSONString(new FileParam()), false);
        //数据体文件参数
        String pointObjectData = jobDatasourceVo.getPointObjectData();
        String[] split = StringUtils.split(pointObjectData, ",");
        FilePath filePathBody = JobFilePathManage.getPathBody(jobDatasourceVo, jobInfoVo);
        for (String key : split) {
            //循环读取指向文件 最大读取1m之内数据
            String pointPath = filePathBody.getPath().replace(JobFilePathManage.POINT_DATA, key);
            //设置传入脚本路径
            FileParamObject fileParamObjectData = new FileParamObject();
            fileParamObjectData.setJobDatasourceVo(jobDatasourceVo);
            FilePath filePath = new FilePath();
            BeanUtil.copyProperties(filePathBody, filePath);
            filePath.setPath(pointPath);
            fileParamObjectData.setFilePath(filePath);
            map.put(JobFilePathManage.getDataKey(jobDatasourceVo, key), fileParamObjectData);
            //写入文件 当前文件没内容
            FileUtils.writer(filePathBody.getRoot(), filePathBody.getTenantCode(), pointPath, "", false);
        }
        return map;
    }

    //根据数据源  输出接口
    public void callInterfaceForJobDatasourceVo(JobDatasourceVo jobDatasourceVo, FileParam fileParam) {
        //校验id
        Long pointObjectId = jobDatasourceVo.getPointObjectId();
        if (pointObjectId == null) {
            throw new BizException("数据源指向接口有误".concat(JSON.toJSONString(jobDatasourceVo)));
        }
        try {
            //调用接口
            InterfaceCallRequest interfaceCallRequest = getRequestInfoByInterfaceId(pointObjectId);
            interfaceCallRequest.setDescribe(jobDatasourceVo.getJobCode().concat("任务："));
            Map<String, Object> header = interfaceCallRequest.getHeader();
            header = header == null ? new HashMap<>() : header;
            Map<String, Object> query = interfaceCallRequest.getQuery();
            query = query == null ? new HashMap<>() : query;

            header.putAll(fileParam.getHeader());
            query.putAll(fileParam.getQuery());

            interfaceCallRequest.setHeader(header);
            interfaceCallRequest.setQuery(header);
            interfaceCallRequest.setBody(JSON.toJSONString(fileParam.getBody()));
//            interfaceCallRequest.setBody("");测试接口用
            String body = interfaceCallRequest.getBody();
            InterfaceResult call = interfaceCall.call(interfaceCallRequest);
            if (!call.getIsSuccess()) {
                throw new BizException("接口参数返回错误:".concat(JSON.toJSONString(call)));
            }
        } catch (Exception e) {
            log.error("输出数据源调用接口出错：".concat(JSON.toJSONString(jobDatasourceVo)), e);
            throw new BizException("输出数据源调用接口出错：".concat(JSON.toJSONString(jobDatasourceVo)), e);
        }
    }

    //根据数据源  读取文件参数 调用脚本的输出接口
    public void readFileForCallInterface(JobDatasourceVo jobDatasourceVo, JobInfoVo jobInfoVo) {

        //读取文件
        FilePath filePathParam = JobFilePathManage.getPathParam(jobDatasourceVo, jobInfoVo);
        String param = FileUtils.readString(filePathParam.getRoot(), filePathParam.getTenantCode(), filePathParam.getPath());
        FileParam fileParam = JSON.parseObject(param, FileParam.class);
        //没有内容不调接口
        if (fileParam == null || fileParam.getBody() == null) {
            return;
        }
        String pointObjectData = jobDatasourceVo.getPointObjectData();
        String[] split = StringUtils.split(pointObjectData, ",");
        FilePath filePathBody = JobFilePathManage.getPathBody(jobDatasourceVo, jobInfoVo);
        Object bodyJson = JSON.parse(JSON.toJSONString(fileParam.getBody()));
        //此处循环调用 分批发送调用接口
        boolean isSend = true;
        BodyPointObject bodyPointObject = new BodyPointObject();
        int index = 0; //限制次数，避免死循环
        do {
            try {
                index++;
                log.info("输出数据源调用接口次数：{}---{}---", index, JSON.toJSONString(jobDatasourceVo));
                long sleepTime = bodyPointObject.getSleepTime();
                if (sleepTime != 0L) {
                    log.info("输出数据源休眠时间：{}---{}---", sleepTime, JSON.toJSONString(jobDatasourceVo));
                    Thread.sleep(sleepTime);
                }
                isSend = true;
                for (String key : split) {
                    boolean result = replaceReaderPointData(fileParam, key, bodyJson, filePathBody, bodyPointObject);
                    if (!result) {
                        isSend = false;
                    }
                }
                //分批发送
                //调用接口
                callInterfaceForJobDatasourceVo(jobDatasourceVo, fileParam);
            } catch (Exception e) {
                log.error("输出数据源调用接口出错：".concat(JSON.toJSONString(jobDatasourceVo)), e);
            }
        } while (!isSend && index < 100_000);

    }
}
