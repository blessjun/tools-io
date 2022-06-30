
package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.core.util.Identities;
import cn.com.coho.tools.io.mapper.SysInterfaceParamMapper;
import cn.com.coho.tools.io.model.entity.SysInterfaceParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author scott
 * 接口参数业务类
 */
@Service
public class SysInterfaceParamService extends ServiceImpl<SysInterfaceParamMapper, SysInterfaceParam> {


    /**
     * 根据接口id获取分类接口参数数据
     * @param interfaceId 接口id
     * @return 已分类接口参数数据
     */
    public Map<String, List<SysInterfaceParam>> getInfoByInterfaceIdGroupByCate(Long interfaceId){

        Map<String, List<SysInterfaceParam>> result = new HashMap<>(2^3);

        List<SysInterfaceParam> sysInterfaceParams = getInfoByInterfaceId(interfaceId);

        if (!sysInterfaceParams.isEmpty()){
           return sysInterfaceParams.stream().collect(Collectors.groupingBy(SysInterfaceParam::getCategory));
        }
        return result;
    }



    public List<SysInterfaceParam> getInfoByInterfaceId(Long interfaceId){
       return this.list(new LambdaQueryWrapper<SysInterfaceParam>().eq(SysInterfaceParam::getInterfaceId, interfaceId));
    }


    public boolean removeByInterfaceId(Long interfaceId){
        return this.remove(new LambdaQueryWrapper<SysInterfaceParam>().eq(SysInterfaceParam::getInterfaceId, interfaceId));
    }


    /**
     * 保存接口参数信息
     * @param interfaceId
     * @param sysInterfaceParams
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAll(Long interfaceId, List<SysInterfaceParam> sysInterfaceParams){
        this.removeByInterfaceId(interfaceId);
        sysInterfaceParams.forEach(param->{
            param.setInterfaceId(interfaceId);
            param.setId(Identities.randomLong());
        });
        return this.saveBatch(sysInterfaceParams);
    }
}

