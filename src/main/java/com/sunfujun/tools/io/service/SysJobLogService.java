
package com.sunfujun.tools.io.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunfujun.tools.io.mapper.SysJobLogMapper;
import com.sunfujun.tools.io.model.entity.SysJobLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author scott
 */
@Service
public class SysJobLogService extends ServiceImpl<SysJobLogMapper, SysJobLog> {

    private static final Logger log = LoggerFactory.getLogger(SysJobLogService.class);

//    @Autowired
//    private SysJobInterfaceRuleService jobInterfaceRuleRelaService;

    public void addJobLog(Long jobId, Date triggerTime, String msg){

//        jobInterfaceRuleRelaService

    }


}

