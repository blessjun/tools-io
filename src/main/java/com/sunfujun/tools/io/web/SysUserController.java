package com.sunfujun.tools.io.web;

import com.sunfujun.tools.io.core.cahe.LocalCache;
import com.sunfujun.tools.io.core.filter.NoAuth;
import com.sunfujun.tools.io.model.dto.LoginDto;
import com.sunfujun.tools.io.model.vo.Result;
import com.sunfujun.tools.io.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author scott
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private LocalCache localCache;

    @NoAuth
    @PostMapping(value = "/login")
    public Result<String> login(@RequestBody LoginDto dto){
        return sysUserService.login(dto);
    }



    @NoAuth
    @GetMapping(value = "/cacheList")
    public Result<Map<String, Object>> cacheList(){
        return Result.buildSuccess(localCache.cacheList());
    }


    @PostMapping(value = "/clearCache")
    public Result<Boolean> clearCache(){
        return Result.buildSuccess(sysUserService.clearCache());
    }
}
