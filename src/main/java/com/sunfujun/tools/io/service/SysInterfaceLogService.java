
package com.sunfujun.tools.io.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunfujun.tools.io.core.util.Identities;
import com.sunfujun.tools.io.mapper.SysInterfaceLogMapper;
import com.sunfujun.tools.io.model.entity.SysInterfaceLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author scott
 * 接口记录业务类
 */
@Service
public class SysInterfaceLogService extends ServiceImpl<SysInterfaceLogMapper, SysInterfaceLog> {


    /**
     * 添加接口调用日志接口，暂未使用
     * @param log
     * @return
     */
    public boolean addInterfaceCallLog(SysInterfaceLog log){
        log.setId(Identities.randomLong());
        return this.save(log);
    }


    /**
     * 查询接口返回最近10条记录
     * @param interfaceId
     * @return
     */
    public List<SysInterfaceLog> interfaceFiveLogs(Long interfaceId){
        return this.list(new LambdaQueryWrapper<SysInterfaceLog>().eq(SysInterfaceLog::getInterfaceId, interfaceId).orderByDesc(SysInterfaceLog::getCallTime).last("limit 10"));
    }

    /**
     * 查询接口调用最后一条记录
     * @param interfaceId
     * @return
     */
    public SysInterfaceLog interfaceLastLog(Long interfaceId){
        return this.getOne(new LambdaQueryWrapper<SysInterfaceLog>().eq(SysInterfaceLog::getInterfaceId, interfaceId).orderByDesc(SysInterfaceLog::getCallTime).last("limit 1"));
    }


}

