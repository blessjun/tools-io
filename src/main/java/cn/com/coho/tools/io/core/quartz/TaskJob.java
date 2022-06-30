package cn.com.coho.tools.io.core.quartz;

import cn.com.coho.tools.io.core.util.Constants;
import cn.com.coho.tools.io.model.entity.SysJobChain;
import cn.com.coho.tools.io.model.vo.JobChainVo;
import cn.com.coho.tools.io.service.SysJobChainService;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author scott
 * 任务执行类
 */
@Component
public class TaskJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(TaskJob.class);

    private static final Map<String, Boolean> jobMap = new ConcurrentHashMap();


    @Autowired
    private SysJobChainService sysJobChainService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        Trigger trigger = jobExecutionContext.getTrigger();
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        try {
            String jobid = jobName.split("_")[1];
            log.info("任务:{}执行 TaskJob:::{}:::{}", jobName, Thread.currentThread().getName(), SimpleDateFormat.getDateTimeInstance().format(new Date()));
            //查询任务
            JobChainVo jobChainVo = sysJobChainService.query(Long.parseLong(jobid)).getData();
            Scheduler scheduler = jobExecutionContext.getScheduler();
            if (jobChainVo == null) {
                log.error("查询不到任务:{}", jobName);
                JobKey jobKey = JobKey.jobKey(jobName, Constants.JOB_GROUP_NAME);
                scheduler.deleteJob(jobKey);
                return;
            }
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            //如果不是当前ip开启的 不执行任务
            if (!jobChainVo.getRemark().equals(hostAddress)) {
                log.info("当前服务没有开启任务");
                return;
            }

            //同步执行
            execute(trigger, jobid, jobChainVo);
        } catch (Exception e) {
            log.error("{}:执行失败,跳过此次任务执行。异常信息：{}", jobName, e);
        }
    }

    //按任务同步执行代码
    private void execute(Trigger trigger, String jobid, JobChainVo jobChainVo) {
        String jobMapKey = String.valueOf(jobChainVo.getId());
        //如过之前任务没有完成 不执行
        Boolean flag = jobMap.get(jobMapKey);
        if (flag != null && flag) {
            log.info("任务正在执行，跳过此次定时执行。{}", jobMapKey);
            return;
        }
        synchronized (jobMapKey.intern()) {
            try {
                jobMap.put(jobMapKey, true);
                // 设置下次执行时间
                SysJobChain sysJobChain = sysJobChainService.getById(Long.parseLong(jobid));
                sysJobChain.setTriggerLastTime(trigger.getPreviousFireTime());
                sysJobChain.setTriggerNextTime(trigger.getNextFireTime());
                sysJobChainService.saveOrUpdate(sysJobChain);
                //调用任务链执行
                sysJobChainService.excuteJob(jobChainVo);
            } finally {
                jobMap.put(jobMapKey, false);
            }

        }
    }


//    /**
//     * 执行接口
//     * @param rules 接口和规则信息
//     * @return 接口执行结果
//     * @throws Exception 执行接口异常
//     */
//    private Map<String, Object> execute(List<SysJobInterfaceRule> rules) throws Exception {
//        Map<String, Object> requestMap = new HashMap<>(2^3);
//        for (SysJobInterfaceRule rule : rules) {
//            InterfaceResult result =  interfaceCall.call(sysInterfaceService.getRequestInfoByInterfaceId(rule.getInterfaceId()));
//            if(!result.getIsSuccess()){
//                log.info("任务id:{}调用接口:{}失败：{}", rule.getJobId(), rule.getInterfaceId(), JsonUtil.toJSONString(result));
//                break;
//                //TODO 接口调用失败，插入失败日志，任务结束
//            }
//            List<String> responseBodies = new ArrayList<>();
//            for (SysInterfaceLog interfaceLog: result.getLogList()){
//                responseBodies.add(interfaceLog.getResponseBody());
//            }
//            requestMap.put(rule.getInterfaceCode(), JsonUtil.toJSONString(responseBodies));
//        }
//        //调用接口
//        return requestMap;
//    }

//    private Boolean engineExecute(String rule, String ruleName, Map<String, Object> requestMap) {
////        try {
////            return (Boolean) engine.excute(rule, requestMap);
////        } catch (RuleEngineExcuteException e) {
////            log.error("规则:{}执行异常:{}", ruleName, e);
////        }
//        return false;
//    }
}
