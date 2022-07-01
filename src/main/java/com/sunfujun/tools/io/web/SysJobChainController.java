package com.sunfujun.tools.io.web;

import com.sunfujun.tools.io.model.dto.IdDto;
import com.sunfujun.tools.io.model.dto.IdsDto;
import com.sunfujun.tools.io.model.dto.JobChainDto;
import com.sunfujun.tools.io.model.dto.StatusDto;
import com.sunfujun.tools.io.model.entity.SysJobChain;
import com.sunfujun.tools.io.model.vo.JobChainVo;
import com.sunfujun.tools.io.model.vo.Result;
import com.sunfujun.tools.io.service.SysJobChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author scott
 * @desc 任务管理
 */
@CrossOrigin
@RestController
@RequestMapping("/jobChain")
public class SysJobChainController {


    @Autowired
    private SysJobChainService sysJobChainService;


    /**
     * 新增/编辑任务链
     */
    @PostMapping("/addOrUpd")
    @Transactional
    public Result<Boolean> addOrUpd(@RequestBody JobChainDto dto) {
        //保存任务链
        Result<Boolean> booleanResult = sysJobChainService.addOrUpd(dto);
        return booleanResult;
    }

    /**
     * 任务链列表
     */
    @PostMapping("/list")
    public Result<List<SysJobChain>> list() {
        return Result.buildSuccess(sysJobChainService.list());
    }


    /**
     * 根据id查询任务链详情
     */
    @PostMapping("/query")
    public Result<JobChainVo> query(@RequestBody IdDto dto) {

        return sysJobChainService.queryAndJob(dto.getId());
    }


    /**
     * 删除链任务
     */
    @PostMapping("/del")
    public Result<Boolean> del(@RequestBody IdsDto dto) {

        Result<Boolean> booleanResult1 = sysJobChainService.del(dto.getIds());
        return booleanResult1;
    }


    /**
     * 启动或停止任务
     */
    @PostMapping("/statusUpd")
    public Result<Boolean> statusUpd(@RequestBody StatusDto dto) {
        return sysJobChainService.statusUpd(dto);
    }

    /**
     * 启动或停止任务
     */
    @PostMapping("/flushAllJob")
    public Result<Boolean> flushAllJob() {
        return sysJobChainService.flushAllJob();
    }
}
