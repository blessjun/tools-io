
package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.core.exception.BizException;
import cn.com.coho.tools.io.core.groovy.GroovyScriptEngine;
import cn.com.coho.tools.io.core.quartz.QuartzManager;
import cn.com.coho.tools.io.core.util.BeanConvertUtils;
import cn.com.coho.tools.io.core.util.ResponseCode;
import cn.com.coho.tools.io.mapper.SysJobChainMapper;
import cn.com.coho.tools.io.model.dto.JobChainDto;
import cn.com.coho.tools.io.model.dto.JobDto;
import cn.com.coho.tools.io.model.dto.StatusDto;
import cn.com.coho.tools.io.model.entity.SysJobChain;
import cn.com.coho.tools.io.model.entity.SysPomitRule;
import cn.com.coho.tools.io.model.vo.JobChainVo;
import cn.com.coho.tools.io.model.vo.JobDatasourceVo;
import cn.com.coho.tools.io.model.vo.JobInfoVo;
import cn.com.coho.tools.io.model.vo.Result;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SysJobChainService extends ServiceImpl<SysJobChainMapper, SysJobChain> {

    private static final Logger log = LoggerFactory.getLogger(SysJobChainService.class);

    @Autowired
    private SysJobInfoService sysJobInfoService;

    @Autowired
    private QuartzManager quartzManager;

    @Autowired
    private GroovyScriptEngine groovyScriptEngine;

    @Autowired
    private SysPomitRuleService sysPomitRuleService;

    @Autowired
    private SysInterfaceService sysInterfaceService;

    //删除任务链
    @Transactional
    public Result<Boolean> del(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        List<SysJobChain> jobChains = this.listByIds(ids);

        //删除任务链对象
        boolean remove = this.removeByIds(ids);
        //删除当前任务链的全部任务
        for (SysJobChain jobChain : jobChains) {
            String jobIdList = jobChain.getJobIdList();
            List<Long> longList = JSONObject.parseArray(jobIdList, Long.class);
            sysJobInfoService.del(longList);
        }
        //删除定时任务
        for (SysJobChain jobChain : jobChains) {
            quartzManager.deleteScheduleJob(jobChain);
        }
        return Result.buildSuccess(remove);
    }

    //增加或修改任务链 里面的任务集合
    @Transactional
    public Result<Boolean> addOrUpd(JobDto jobDto, String addOrdelete) {
        //查询出任务链
        JobChainVo jobChainVo = query(jobDto.getJobChainId()).getData();
        //过滤要处理的任务
        List<JobInfoVo> jobInfoVoList = jobChainVo.getJobInfoVoList();
        if (jobInfoVoList == null) {
            jobInfoVoList = new ArrayList<>();
        }
        List<JobInfoVo> collect = jobInfoVoList.stream().filter((JobInfoVo jobInfoVo) -> {
            boolean equals = jobInfoVo.getId().equals(jobDto.getId());
            return !equals;
        }).collect(Collectors.toList());
        //转换结果为dto对象
        List<JobDto> jobDtoList = new ArrayList<>();
        if (!collect.isEmpty()) {
            jobDtoList = BeanConvertUtils.convertToList(collect, JobDto::new);
        }
        //增加修改或删除
        if (!"delete".equalsIgnoreCase(addOrdelete)) {
            jobDtoList.add(jobDto);
        }
        //处理属性
        //任务id集合属性
        List<Long> jobIdList = new ArrayList<>();
        List<String> jobCodeList = new ArrayList<>();
        List<String> jobNameList = new ArrayList<>();
        for (JobDto dto : jobDtoList) {
            jobIdList.add(dto.getId());
            jobCodeList.add(dto.getCode());
            jobNameList.add(dto.getName());
        }
        //转换对象
        SysJobChain sysJobChain = this.getById(jobDto.getJobChainId());
        BeanUtil.copyProperties(jobChainVo, sysJobChain);
        sysJobChain.setJobIdList(JSONObject.toJSONString(jobIdList));
        //任务编码集合属性
        sysJobChain.setJobCodeList(JSONObject.toJSONString(jobCodeList));
        //任务名称集合属性
        sysJobChain.setJobNameList(JSONObject.toJSONString(jobNameList));
        boolean flag = this.saveOrUpdate(sysJobChain);
        return Result.buildSuccess(flag);
    }

    //增加或修改任务链
    @Transactional
    public Result<Boolean> addOrUpd(JobChainDto jobChainDto) {
        //校验必填参数
        if (jobChainDto == null) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        List<SysJobChain> infos = this.list();

        //校验编码重复
        boolean present = infos.stream().anyMatch(m -> m.getCode().equals(jobChainDto.getCode()) && !m.getId().equals(jobChainDto.getId()));
        if (present) {
            return Result.newInstance(ResponseCode.CODE_REPEAT);
        }
        //检验名称重复
        present = infos.stream().anyMatch(m -> m.getName().equals(jobChainDto.getName()) && !m.getId().equals(jobChainDto.getId()));
        if (present) {
            return Result.newInstance(ResponseCode.NAME_REPEAT);
        }

        SysJobChain info = this.getById(jobChainDto.getId());
        //校验id 重复
        if (info == null) {
            info = new SysJobChain();
        }
        //复制参数
        BeanUtil.copyProperties(jobChainDto, info);

        //修改定时任务执行
        quartzManager.updateScheduleJob(info);
        boolean flag = this.saveOrUpdate(info);
        if (!flag) {
            throw new BizException("增加失败");
        }
        return Result.buildSuccess(flag);
    }

    //根据id查询任务链修正任务
    @Transactional
    public Result<JobChainVo> queryAndJob(Long jobChainId) {
        //校验参数
        if (jobChainId == null || jobChainId == 0L) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        SysJobChain info = this.getById(jobChainId);
        //不存在此任务
        if (info == null) {
            return Result.newInstance(ResponseCode.NOT_FOUND);
        }
        //修改定时任务执行
        quartzManager.updateScheduleJob(info);
        this.saveOrUpdate(info);
        return query(jobChainId);
    }


    //根据id查询任务链
    public Result<JobChainVo> query(Long jobChainId) {
        //校验参数
        if (jobChainId == null || jobChainId == 0L) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }

        SysJobChain info = this.getById(jobChainId);
        //不存在此任务
        if (info == null) {
            return Result.newInstance(ResponseCode.NOT_FOUND);
        }
        //返回查询结果
        JobChainVo vo = new JobChainVo();
        BeanUtil.copyProperties(info, vo);
        //查询任务集合
        String jobIdList = vo.getJobIdList();
        if (jobIdList != null && !jobIdList.isEmpty()) {
            List<Long> longs = JSONObject.parseArray(jobIdList, Long.class);
            List<JobInfoVo> jobInfoVos = sysJobInfoService.queryByIds(longs);
            //排序
            sysJobInfoService.sort(jobInfoVos);
            vo.setJobInfoVoList(jobInfoVos);
        }

        return Result.buildSuccess(vo);
    }

    //启动或停止任务
    public Result<Boolean> statusUpd(StatusDto dto) {
        if (dto == null || dto.getIds() == null || dto.getIds().isEmpty()) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        List<SysJobChain> jobs = this.listByIds(dto.getIds());

        if (dto.getIsStart()) {
            for (SysJobChain job : jobs) {
                //开启
                quartzManager.createScheduleJob(job);
                job.setStatus(dto.getIsStart());
                //通过ip控制只有一台服务器执行任务
                String hostAddress = null;
                try {
                    hostAddress = Inet4Address.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取服务ip有误", e);
                    throw new BizException("获取服务ip有误", e);
                }
                job.setRemark(hostAddress);
                this.saveOrUpdate(job);
            }
        } else {
            //删除
            for (SysJobChain job : jobs) {
                quartzManager.deleteScheduleJob(job);
                job.setStatus(dto.getIsStart());
                this.saveOrUpdate(job);
            }
        }
        return Result.buildSuccess(true);
    }

    //清理全部不存在定时任务
    public Result<Boolean> flushAllJob() {
        Set<JobKey> jobKeys = quartzManager.getAllJobIds();
        for (JobKey jobKey : jobKeys) {
            String jobName = jobKey.getName();
            String jobid = jobName.split("_")[1];
            SysJobChain info = this.getById(Long.parseLong(jobid));
            //清除不存在的定时任务
            if (info == null) {
                quartzManager.deleteScheduleJob(jobKey);
            } else {
                quartzManager.updateScheduleJob(info);
                this.saveOrUpdate(info);
            }
        }
        return Result.buildSuccess(true);
    }

    //执行具体定时任务
    public void excuteJob(JobChainVo jobChainVo) {
        List<JobInfoVo> jobInfoVoList = jobChainVo.getJobInfoVoList();
        //排序执行任务
        sysJobInfoService.sort(jobInfoVoList);
        //开始执行
        for (JobInfoVo jobInfoVo : jobInfoVoList) {
            List<JobDatasourceVo> jobDatasourceList = jobInfoVo.getJobDatasourceList();
            //获取脚本参数  每个数据源对应一个实体
            Map<String, Object> map = new HashMap<>();
            for (JobDatasourceVo jobDatasourceVo : jobDatasourceList) {
                Map<String, Object> objectMap = sysInterfaceService.readFileByJobDatasourceVo(jobDatasourceVo, jobInfoVo);
                map.putAll(objectMap);
            }
            //过滤出入参数据源
            List<JobDatasourceVo> jobDatasourceVoCollectInto = jobDatasourceList.stream().filter((JobDatasourceVo vo) -> {
                return vo.getJobParamType() == 1;
            }).collect(Collectors.toList());
            //调用远程入参接口
            for (JobDatasourceVo jobDatasourceVo : jobDatasourceVoCollectInto) {
                sysInterfaceService.callInterfaceToFile(jobDatasourceVo, jobInfoVo);
            }
            //执行groovy
            SysPomitRule sysPomitRule = sysPomitRuleService.getById(jobInfoVo.getRuleId());
            if (sysPomitRule == null) {
                log.error("任务{}找不到执行规则", jobInfoVo.getName());
                throw new BizException("任务".concat(jobInfoVo.getName()).concat("找不到执行规则"));
            }
            groovyScriptEngine.executeObject(sysPomitRule.getRule(), map);

            //过滤出出参数据源
            List<JobDatasourceVo> jobDatasourceVoCollectOut = jobDatasourceList.stream().filter((JobDatasourceVo vo) -> {
                return vo.getJobParamType() == 2;
            }).collect(Collectors.toList());
            //调用远程出参接口
            for (JobDatasourceVo jobDatasourceVo : jobDatasourceVoCollectOut) {
                sysInterfaceService.readFileForCallInterface(jobDatasourceVo, jobInfoVo);
            }
        }
    }
}

