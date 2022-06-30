
package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.mapper.SysJobLogMapper;
import cn.com.coho.tools.io.model.entity.SysJobLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

