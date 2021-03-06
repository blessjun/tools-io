package com.sunfujun.tools.io.web;

import com.sunfujun.tools.io.core.call.InterfaceResult;
import com.sunfujun.tools.io.model.dto.*;
import com.sunfujun.tools.io.model.entity.SysInterfaceGroup;
import com.sunfujun.tools.io.model.entity.SysInterfaceInfo;
import com.sunfujun.tools.io.model.entity.SysInterfaceLog;
import com.sunfujun.tools.io.model.vo.InterfaceInfoVo;
import com.sunfujun.tools.io.model.vo.Result;
import com.sunfujun.tools.io.model.vo.SimpleVo;
import com.sunfujun.tools.io.service.SysInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author scott
 */
@CrossOrigin
@RestController
@RequestMapping("/interface")
public class SysInterfaceController {


    @Autowired
    private SysInterfaceService sysInterfaceService;


    @PostMapping("/list")
    public Result<List<SysInterfaceInfo>> list(@RequestBody(required = false) InterfaceSearchDto dto){
        return sysInterfaceService.list(dto);
    }


    @PostMapping("/query")
    public Result<InterfaceInfoVo> query(@RequestBody IdDto dto){
        return sysInterfaceService.query(dto.getId());
    }



    @PostMapping("/save")
    public Result<Long> save(@RequestBody InterfaceDto dto) throws Exception {
        return sysInterfaceService.save(dto);
    }


    @PostMapping("/call")
    public Result<InterfaceResult> call(@RequestBody InterfaceDto dto) throws Exception {
        return sysInterfaceService.call(dto);
    }

    /**
     * 删除接口
     */
    @PostMapping("/del")
    public Result<Boolean> del(@RequestBody IdsDto dto){
        return sysInterfaceService.del(dto.getIds());
    }

    @PostMapping("/stateUpd")
    public Result<Boolean> stateUpd(@RequestBody StateUpdDto dto){
        return sysInterfaceService.stateUpd(dto);
    }


    @PostMapping("/groupList")
    public Result<List<SysInterfaceGroup>> groupList(){
        return sysInterfaceService.groupList();
    }


    @PostMapping("/addOrUpdGroup")
    public Result<Boolean> addOrUpdGroup(@RequestBody InterfaceGroupDto dto){
        return sysInterfaceService.addOrUpdGroup(dto);
    }



    @PostMapping("/listBox")
    public Result<List<SimpleVo>> listBox(){
        return sysInterfaceService.listBox();
    }


    @PostMapping("/interfaceCallRecords")
    public Result<List<SysInterfaceLog>> interfaceCallRecords(@RequestBody IdDto dto){
        return sysInterfaceService.interfaceCallRecords(dto);
    }
}
