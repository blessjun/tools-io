
package com.sunfujun.tools.io.web;

import com.sunfujun.tools.io.model.dto.IdsDto;
import com.sunfujun.tools.io.model.dto.RuleDto;
import com.sunfujun.tools.io.model.entity.SysPomitRule;
import com.sunfujun.tools.io.model.vo.Result;
import com.sunfujun.tools.io.service.SysPomitRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rule")
@CrossOrigin
@RestController
public class SysPomitRuleController {


    @Autowired
    private SysPomitRuleService sysPomitRuleService;


    /**
     * 新增/编辑任务
     */
    @PostMapping("/addOrUpd")
    public Result<Boolean> addOrUpd(@RequestBody RuleDto dto){
        return sysPomitRuleService.addOrUpd(dto);
    }

    /**
     * 任务列表
     */
    @PostMapping("/list")
    public Result<List<SysPomitRule>> list(){
        return Result.buildSuccess(sysPomitRuleService.list());
    }


    /**
     * 删除任务
     */
    @PostMapping("/del")
    public Result<Boolean> del(@RequestBody IdsDto dto){
        return sysPomitRuleService.del(dto.getIds());
    }



    @PostMapping("/listBox")
    public Result<List<SysPomitRule>> listBox(){
        return Result.buildSuccess(sysPomitRuleService.listBox());
    }

}

