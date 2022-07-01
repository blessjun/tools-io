package com.sunfujun.tools.io.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sunfujun.tools.io.core.quartz.QuartzManager;
import com.sunfujun.tools.io.model.entity.SysJobChain;
import com.sunfujun.tools.io.service.SysJobChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author scott
 * 项目启动时执行
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MyCommandLineRunner.class);

    @Autowired
    private SysJobChainService sysJobChainService;

    @Autowired
    private QuartzManager quartzManager;


    @Override
    public void run(String... args) {
        initJob();
    }

    private void initJob() {
        // 初始化所有的已经启用的订阅
        List<SysJobChain> jobs = sysJobChainService.list(new LambdaQueryWrapper<SysJobChain>().eq(SysJobChain::getStatus, true));
        log.info("需要初始化的任务个数:{}", jobs.size());
        for (SysJobChain job : jobs) {
            try {
                log.info("开始初始化任务,任务name:{}", job.getName());
                quartzManager.createScheduleJob(job);
            } catch (Exception e) {
                log.error("启动时初始化任务失败:{}", e.getMessage());
            }
        }
        sysJobChainService.saveOrUpdateBatch(jobs);
    }
}
