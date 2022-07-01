package com.sunfujun.tools.io.core.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunfujun.tools.io.core.cahe.LocalCache;
import com.sunfujun.tools.io.core.cahe.RequestDataContext;
import com.sunfujun.tools.io.core.exception.BizException;
import com.sunfujun.tools.io.core.util.Constants;
import com.sunfujun.tools.io.core.util.ResponseCode;
import com.sunfujun.tools.io.model.entity.UserLoginData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 接口拦截器
 * @author: scott
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

    @Autowired
    private LocalCache localCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BizException, JsonProcessingException {
        log.info("request start. url: {}", request.getRequestURL());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoAuth noAuth = handlerMethod.getMethodAnnotation(NoAuth.class);
            if (noAuth == null) {
                String token = request.getHeader(Constants.AUTH_KEY);
                log.debug("header key: {}, token: {}", Constants.AUTH_KEY, token);
                if (StrUtil.isEmpty(token)) {
                    log.info("token is null");
                    throw new BizException(ResponseCode.ERROR_AUTH);
                }

                UserLoginData userLoginData = (UserLoginData) localCache.get(token);

                if (userLoginData != null) {
                    RequestDataContext.set(userLoginData);
                    return true;
                }

                log.error("token {} invalid", token);
                throw new BizException(ResponseCode.ERROR_AUTH);
            }
        } else if (handler instanceof ResourceHttpRequestHandler) {
            log.info("this is static resource!");
            return true;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestDataContext.clear();
    }
}
