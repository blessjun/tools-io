
package cn.com.coho.tools.io.service;

import cn.com.coho.tools.io.core.cahe.LocalCache;
import cn.com.coho.tools.io.core.exception.BizException;
import cn.com.coho.tools.io.core.util.Constants;
import cn.com.coho.tools.io.core.util.Identities;
import cn.com.coho.tools.io.core.util.ResponseCode;
import cn.com.coho.tools.io.mapper.SysUserMapper;
import cn.com.coho.tools.io.model.dto.LoginDto;
import cn.com.coho.tools.io.model.entity.SysUser;
import cn.com.coho.tools.io.model.entity.UserLoginData;
import cn.com.coho.tools.io.model.vo.Result;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author scott
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    private static final Logger log = LoggerFactory.getLogger(SysUserService.class);

    @Autowired
    private LocalCache localCache;


    public Result<String> login(LoginDto dto) throws BizException {
        if (StrUtil.isEmpty(dto.getUserName()) || StrUtil.isEmpty(dto.getPassword())) {
            return Result.newInstance(ResponseCode.LOGIN_PARAM_NULL);
        }
        SysUser user = this.baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, dto.getUserName()));

        if (user == null) {
            return Result.newInstance(ResponseCode.LOGIN_USER_NOT_FOUND);
        }
        String password = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)).toUpperCase();
        if (!user.getPassword().equals(password)) {
            return Result.newInstance(ResponseCode.LOGIN_FAIL);
        }
        //生成token
        String token = Identities.uuid();
        UserLoginData userLoginData = new UserLoginData(user.getId(), user.getUserName());
        localCache.put(token, userLoginData, Constants.TOKEN_TIME_OUT);
        return Result.buildSuccess(token);
    }





    public Boolean clearCache(){
        localCache.clear();
        return true;
    }
}

