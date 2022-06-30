
package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.core.util.ResponseCode;
import cn.com.coho.tools.io.mapper.SysParamMapper;
import cn.com.coho.tools.io.model.dto.InterfaceSearchDto;
import cn.com.coho.tools.io.model.dto.ParamDto;
import cn.com.coho.tools.io.model.entity.SysParam;
import cn.com.coho.tools.io.model.vo.Result;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author scott
 * 系统参数业务类
 */
@Service
public class SysParamService extends ServiceImpl<SysParamMapper, SysParam> {

    //编辑系统参数
    @Transactional(rollbackFor = Exception.class)
    public Boolean addOrUpd(ParamDto dto) {
        SysParam param =  this.getById(dto.getId());

        if (param == null){
            param = new SysParam();
        }

        BeanUtil.copyProperties(dto, param);

        return this.saveOrUpdate(param);
    }


    /*删除参数列表*/
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> del(List<Long> ids) {

        if (ids == null || ids.isEmpty()){
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        return Result.buildSuccess(this.removeByIds(ids));
    }


    /*系统参数列表*/
    public Result<List<SysParam>> listResult(InterfaceSearchDto dto){
        if (dto == null || dto.getPageIndex() == null || dto.getPageSize() == null) {
            return Result.buildSuccess(this.list());
        }
        Page<SysParam> page = new Page<>(dto.getPageIndex(), dto.getPageSize());
        LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(dto.getKeyWord())){
            queryWrapper.like(SysParam::getName, dto.getKeyWord());
        }
        if (dto.getGroupId() != null){
            queryWrapper.eq(SysParam::getGroupId, dto.getGroupId());
        }
        Page<SysParam> sysParamPage = this.getBaseMapper().selectPage(page, queryWrapper);
        return Result.buildSuccess(sysParamPage.getRecords(), (int) sysParamPage.getTotal());
    }
}

