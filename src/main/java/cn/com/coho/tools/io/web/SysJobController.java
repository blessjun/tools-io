package cn.com.coho.tools.io.web;

import cn.com.coho.tools.io.model.dto.IdDto;
import cn.com.coho.tools.io.model.dto.JobDto;
import cn.com.coho.tools.io.model.entity.SysJobInfo;
import cn.com.coho.tools.io.model.vo.JobInfoVo;
import cn.com.coho.tools.io.model.vo.Result;
import cn.com.coho.tools.io.service.SysJobChainService;
import cn.com.coho.tools.io.service.SysJobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author scott
 * @desc 任务管理
 */
@CrossOrigin
@RestController
@RequestMapping("/job")
public class SysJobController {


    @Autowired
    private SysJobInfoService jobInfoService;

    @Autowired
    private SysJobChainService sysJobChainService;


    /**
     * 新增/编辑任务链
     */
    @PostMapping("/addOrUpd")
    @Transactional
    public Result<Boolean> addOrUpd(@RequestBody JobDto dto) {
        //保存任务
        Result<Boolean> booleanResult = jobInfoService.addOrUpd(dto);
        if (booleanResult.getCode() != 200) {
            return booleanResult;
        }
        //修改任务链
        Result<Boolean> booleanResult1 = sysJobChainService.addOrUpd(dto, "");
        return booleanResult1;
    }

    /**
     * 任务列表
     */
    @PostMapping("/list")
    public Result<List<SysJobInfo>> list() {
        return Result.buildSuccess(jobInfoService.list());
    }


    @PostMapping("/query")
    public Result<JobInfoVo> query(@RequestBody IdDto dto) {
        return jobInfoService.query(dto);
    }


    /**
     * 删除任务
     */
    @PostMapping("/del")
    public Result<Boolean> del(@RequestBody JobDto dto) {
        //删除任务
        Result<Boolean> del = jobInfoService.del(Arrays.asList(dto.getId()));
        if (del.getCode() != 200) {
            return del;
        }
        //修改任务链
        Result<Boolean> booleanResult1 = sysJobChainService.addOrUpd(dto, "delete");
        return booleanResult1;
    }
}
