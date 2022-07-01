
package com.sunfujun.tools.io.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunfujun.tools.io.core.util.JsonUtil;
import com.sunfujun.tools.io.core.util.ParamTypeEnum;
import com.sunfujun.tools.io.core.util.ResponseCode;
import com.sunfujun.tools.io.mapper.SysInterfaceInfoMapper;
import com.sunfujun.tools.io.model.dto.InterfaceSearchDto;
import com.sunfujun.tools.io.model.entity.SysInterfaceInfo;
import com.sunfujun.tools.io.model.entity.SysInterfaceParam;
import com.sunfujun.tools.io.model.vo.InterfaceInfoVo;
import com.sunfujun.tools.io.model.vo.Result;
import com.sunfujun.tools.io.model.vo.SimpleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author scott
 * 接口业务处理类
 */
@Service
public class SysInterfaceInfoService extends ServiceImpl<SysInterfaceInfoMapper, SysInterfaceInfo> {

    private static final Logger log = LoggerFactory.getLogger(SysInterfaceInfoService.class);

    @Autowired
    private SysInterfaceParamService interfaceParamService;


    @Autowired
    private SysInterfaceLogService sysInterfaceLogService;


    /**
     * 接口列表
     * @param dto 接口列表入参
     * @return 接口数据
     */
    public Result<List<SysInterfaceInfo>> listResult(InterfaceSearchDto dto){
        Page<SysInterfaceInfo> page = new Page<>(dto.getPageIndex(), dto.getPageSize());
        LambdaQueryWrapper<SysInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(dto.getKeyWord())){
            queryWrapper.like(SysInterfaceInfo::getName, dto.getKeyWord()).or().like(SysInterfaceInfo::getCode, dto.getKeyWord());
        }
        if (dto.getGroupId() != null){
            queryWrapper.eq(SysInterfaceInfo::getGroupId, dto.getGroupId());
        }
        Page<SysInterfaceInfo> SysInterfaceInfoPage = this.getBaseMapper().selectPage(page, queryWrapper);
        return Result.buildSuccess(SysInterfaceInfoPage.getRecords(), (int) SysInterfaceInfoPage.getTotal());
    }

    public Result<Boolean> del(List<Long> ids){
        if (ids == null || ids.isEmpty()){
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        return Result.buildSuccess(this.removeByIds(ids));
    }


    /**
     * 根据id查询接口详情
     * @param id 接口id
     * @return 接口信息
     */
    public Result<InterfaceInfoVo> query(Long id) {
        if (id == null || id == 0L){
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        InterfaceInfoVo vo  = new InterfaceInfoVo();
        SysInterfaceInfo info = this.getById(id);
        if (info == null){
            return Result.newInstance(ResponseCode.NOT_FOUND);
        }
        BeanUtil.copyProperties(info, vo);
        vo.setLoopParams(JsonUtil.parse(info.getLoopParam(), Map.class));
        vo.setParams(interfaceParamService.getInfoByInterfaceIdGroupByCate(id));
        vo.setResponse(sysInterfaceLogService.interfaceLastLog(info.getId()));
        return Result.buildSuccess(vo);
    }


    /**
     * 新增或编辑接口信息
     * @param info 接口信息
     * @param params 参数信息
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrUpd(SysInterfaceInfo info, List<SysInterfaceParam> params) throws Exception{
        return interfaceParamService.saveAll(info.getId(), params) &&  this.saveOrUpdate(info);
    }


    /**
     * 查询包含缓存参数的接口
     * @return
     */
    public List<SimpleVo> findByIsExistCacheParam(){
        List<SimpleVo> vos = new ArrayList<>();
        List<SysInterfaceParam> params = interfaceParamService.list(new LambdaQueryWrapper<SysInterfaceParam>().select(SysInterfaceParam::getName, SysInterfaceParam::getInterfaceId)
                .eq(SysInterfaceParam::getType, ParamTypeEnum.CACHE.getType()));
        if (!params.isEmpty()){
            Map<Long, List<SysInterfaceParam>> paramMap = params.stream().collect(Collectors.groupingBy(SysInterfaceParam::getInterfaceId));
            for (Map.Entry<Long, List<SysInterfaceParam>> entry : paramMap.entrySet()) {
                SysInterfaceInfo info = simpleInterfaceInfo(entry.getKey());
                SimpleVo vo = new SimpleVo(info.getId(),info.getCode(),info.getName(),entry.getValue().stream().map(SysInterfaceParam::getName).collect(Collectors.toList()));
                vos.add(vo);
            }
        }
        return vos;
    }


    /**
     * 简单查询，只查询接口姓名和编码
     */
    public SysInterfaceInfo simpleInterfaceInfo(Long id){
        return this.getOne(new LambdaQueryWrapper<SysInterfaceInfo>().select(SysInterfaceInfo::getId, SysInterfaceInfo::getCode,SysInterfaceInfo::getName).eq(SysInterfaceInfo::getId, id));
    }


    /**
     * 根据code查询id，暂未使用
     * @param code
     * @return
     */
    public Long getInterfaceIdByCode(String code){
        SysInterfaceInfo info =  this.getOne(new LambdaQueryWrapper<SysInterfaceInfo>().select(SysInterfaceInfo::getId).eq(SysInterfaceInfo::getCode, code));
        if (info == null){
            return 0L;
        }
        return info.getId();
    }
}

