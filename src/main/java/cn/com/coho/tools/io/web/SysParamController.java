package cn.com.coho.tools.io.web;

import cn.com.coho.tools.io.model.dto.IdsDto;
import cn.com.coho.tools.io.model.dto.InterfaceSearchDto;
import cn.com.coho.tools.io.model.dto.ParamDto;
import cn.com.coho.tools.io.model.entity.SysParam;
import cn.com.coho.tools.io.model.vo.Result;
import cn.com.coho.tools.io.service.SysParamService;
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
