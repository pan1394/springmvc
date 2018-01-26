package com.yilin.www.spring.token2.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yilin.www.spring.mvc.utils.Constants;
import com.yilin.www.spring.mvc.utils.CookieUtils;
import com.yilin.www.spring.token2.Authorization;
import com.yilin.www.spring.token2.TokenManager;
import com.yilin.www.spring.token2.TokenModel;
import com.yilin.www.spring.token2.designpattern.AuthorizationFacade;

/**
 * 自定义拦截器，判断此次请求是否有权限
 * @author ScienJus
 * @date 2015/7/30.
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;  
    
    @Autowired
    private AuthorizationFacade authFacade;
    
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
       /* String authorization = request.getHeader(Constants.AUTHORIZATION);
        if(authorization == null){
        	 Cookie[] cookies = request.getCookies();
        	 Cookie cookie = CookieUtils.getCookieByName(Constants.AUTHORIZATION, cookies);
        	 authorization = cookie==null? null : cookie.getValue();
        }*/
        String authorization = authFacade.getAuthorizationToken(request);
        //验证token
        TokenModel model = manager.getToken(authorization);
        if (manager.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
            return true;
        }
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}