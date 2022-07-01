package com.sunfujun.tools.io.web;

import com.sunfujun.tools.io.model.dto.IdsDto;
import com.sunfujun.tools.io.model.dto.InterfaceSearchDto;
import com.sunfujun.tools.io.model.dto.ParamDto;
import com.sunfujun.tools.io.model.entity.SysParam;
import com.sunfujun.tools.io.model.vo.Result;
import com.sunfujun.tools.io.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author scott
 */
@RequestMapping("/param")
@CrossOrigin
@RestController
public class SysParamController {



    @Autowired
    private SysParamService sysParamService;


    /**
     *
     */
    @PostMapping("/addOrUpd")
    public Result<Boolean> addOrUpd(@RequestBody ParamDto dto){
       return Result.buildSuccess(sysParamService.addOrUpd(dto));
    }


    @PostMapping("/del")
    public Result<Boolean> del(@RequestBody IdsDto dto){
        return sysParamService.del(dto.getIds());
    }


    @PostMapping("/list")
    public Result<List<SysParam>> list(@RequestBody InterfaceSearchDto dto){
        return sysParamService.listResult(dto);
    }



}
