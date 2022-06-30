
package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.core.util.ResponseCode;
import cn.com.coho.tools.io.mapper.SysPomitRuleMapper;
import cn.com.coho.tools.io.model.dto.RuleDto;
import cn.com.coho.tools.io.model.entity.SysPomitRule;
import cn.com.coho.tools.io.model.vo.Result;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 规则业务类
 */
@Service
public class SysPomitRuleService extends ServiceImpl<SysPomitRuleMapper, SysPomitRule> {

    private static final Logger log = LoggerFactory.getLogger(SysPomitRuleService.class);


    //新增或编辑规则
    public Result<Boolean> addOrUpd(RuleDto dto) {
        if (dto == null) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }

        List<SysPomitRule> rules = this.list();

        //编码校验
        boolean present = rules.stream().anyMatch(m -> m.getName().equals(dto.getName()) && !m.getId().equals(dto.getId()));
        if (present) {
            return Result.newInstance(ResponseCode.NAME_REPEAT);
        }

        SysPomitRule rule = this.getById(dto.getId());
        if (rule == null) {
            rule = new SysPomitRule();
        }

        BeanUtil.copyProperties(dto, rule);

        return Result.buildSuccess(this.saveOrUpdate(rule));
    }


    //删除规则
    public Result<Boolean> del(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.newInstance(ResponseCode.PARAM_FAIL);
        }
        return Result.buildSuccess(this.removeByIds(ids));
    }

    //规则下拉框
    public List<SysPomitRule> listBox() {
        return this.list(new LambdaQueryWrapper<SysPomitRule>().select(SysPomitRule::getId, SysPomitRule::getName));
    }

}

