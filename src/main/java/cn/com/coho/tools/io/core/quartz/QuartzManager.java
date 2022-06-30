package cn.com.coho.tools.io.core.quartz;


import cn.com.coho.tools.io.core.exception.BizException;
import cn.com.coho.tools.io.core.util.Constants;
import cn.com.coho.tools.io.model.entity.SysJobChain;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;


/**
 * 任务管理器
 *
 * @author scott
 */
@Component
public class QuartzManager {

    private static final Logger log = LoggerFactory.getLogger(QuartzManager.class);

    @Autowired
    private Scheduler scheduler;

    /**
     * 创建定时任务 定时任务创建之后默认启动状态
     */
    public void createScheduleJob(SysJobChain sysJobChain) {
        try {
            String jobKey = getJobKey(sysJobChain);
            //获取到定时任务的执行类  必须是类的绝对路径名称
            //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
            Class<? extends Job> jobClass = TaskJob.class;
            // 构建定时任务信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey, Constants.JOB_GROUP_NAME).build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(sysJobChain.getJobCron());
            // 构建触发器trigger
            // 如果已经有下一次时间，就设置为下一次时间为触发时间
            CronTrigger trigger = getCronTrigger(sysJobChain, jobKey, scheduleBuilder);
            scheduler.scheduleJob(jobDetail, trigger);
            // 设置下次执行时间
            sysJobChain.setStatus(true);

            sysJobChain.setTriggerLastTime(trigger.getPreviousFireTime());
            sysJobChain.setTriggerNextTime(trigger.getNextFireTime());
        } catch (SchedulerException e) {
            log.error("创建定时任务失败:".concat(e.getMessage()), e);
            throw new BizException("创建定时任务失败:".concat(e.getMessage()));
        }
    }

    /**
     * 获取定时器
     *
     * @param sysJobChain
     * @param jobKey
     * @param scheduleBuilder
     * @return
     */
    private CronTrigger getCronTrigger(SysJobChain sysJobChain, String jobKey, CronScheduleBuilder scheduleBuilder) {
        CronTrigger trigger;
        if (false && !Objects.isNull(sysJobChain.getTriggerNextTime())) {
            trigger = TriggerBuilder.newTrigger().startAt(sysJobChain.getTriggerNextTime())
                    .withIdentity(sysJobChain.getName(), Constants.JOB_GROUP_NAME).withSchedule(scheduleBuilder).build();
        } else {
            trigger = TriggerBuilder.newTrigger().startNow()
                    .withIdentity(jobKey, Constants.JOB_GROUP_NAME).withSchedule(scheduleBuilder).build();
        }
        return trigger;
    }

//    /**
//     * 根据任务名称暂停定时任务
//     *
//     * @param jobName 定时任务名称（这里直接用任务的名称）
//     */
//    public void pauseScheduleJob(String jobName) throws SchedulerException {
//        JobKey jobKey = JobKey.jobKey(jobName, Constants.JOB_GROUP_NAME);
//        scheduler.pauseJob(jobKey);
//    }

    /**
     * 获取全部任务
     */
    public Set<JobKey> getAllJobIds() {
        // 判断当前任务是否在调度中
        Set<JobKey> jobKeys = null;
        try {
            jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(Constants.JOB_GROUP_NAME));
        } catch (SchedulerException e) {
            log.error("获取全部定时任务失败");
            throw new BizException("更新定时任务失败:".concat(e.getMessage()));
        }
        return jobKeys;
    }

    /**
     * 更新定时任务
     *
     * @param sysJobChain 任务对象
     */
    public void updateScheduleJob(SysJobChain sysJobChain) {
        try {
            String jobName = getJobKey(sysJobChain);
            //获取到对应任务的触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, Constants.JOB_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //不存在直接返回
            if (trigger == null) {
                sysJobChain.setStatus(false);
                return;
            }
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(sysJobChain.getJobCron());
            //重新构建任务的触发器trigger
            trigger = getCronTrigger(sysJobChain, jobName, scheduleBuilder);

            //重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
            // 设置下次执行时间
            sysJobChain.setStatus(true);
            sysJobChain.setTriggerLastTime(trigger.getPreviousFireTime());
            sysJobChain.setTriggerNextTime(trigger.getNextFireTime());
        } catch (Exception e) {
            log.error("更新定时任务失败:".concat(e.getMessage()), e);
            throw new BizException("更新定时任务失败:".concat(e.getMessage()));
        }
    }

    /**
     * 根据定时任务名称从调度器当中删除定时任务
     *
     * @param sysJobChain 定时任务名称
     */
    public void deleteScheduleJob(SysJobChain sysJobChain) {
        try {
            String jobName = getJobKey(sysJobChain);
            JobKey jobKey = JobKey.jobKey(jobName, Constants.JOB_GROUP_NAME);
            //不存在
            if (!scheduler.checkExists(jobKey)) {
                throw new BizException("当前定时任务不存在");
            }
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("删除任务失败:".concat(e.getMessage()), e);
            throw new BizException("删除任务失败:".concat(e.getMessage()));
        }
    }


    /**
     * 根据定时任务名称从调度器当中删除定时任务
     *
     * @param jobKey 定时任务名称
     */
    public void deleteScheduleJob(JobKey jobKey) {
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("删除任务失败:".concat(e.getMessage()), e);
            throw new BizException("删除任务失败:".concat(e.getMessage()));
        }
    }

    /**
     * 获取任务key
     *
     * @param sysJobChain
     * @return
     */
    private String getJobKey(SysJobChain sysJobChain) {
        String id = String.valueOf(sysJobChain.getId());
        String name = sysJobChain.getName();
        return name.concat("_").concat(id);
    }
}
