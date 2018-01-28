package com.yilin.www.spring.mvc.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yilin.www.spring.dao.StudentDao;
import com.yilin.www.spring.mvc.config.ResultStatus;
import com.yilin.www.spring.mvc.model.ResultModel;
import com.yilin.www.spring.mvc.utils.Constants;
import com.yilin.www.spring.mvc.utils.CookieUtils;
import com.yilin.www.spring.token2.Authorization;
import com.yilin.www.spring.token2.TokenManager;
import com.yilin.www.spring.token2.TokenModel;
import com.yilin.www.spring.vo.Student;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 * @author ScienJus
 * @date 2015/7/30.
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private StudentDao userRepository;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private HttpServletRequest req;
    
    @Autowired
    private HttpServletResponse resp;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResultModel> login(@RequestParam Long userId, @RequestParam String password) {
        Assert.notNull(userId, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        List< Student> user = userRepository.getStudent((userId));
        if (user == null ||  //未注册
                //!user.getPassword().equals(password)
                user.size() != 1
        		) {  //密码错误
            //提示用户名或密码错误
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(userId);
        CookieUtils.saveAsSimpleCookie(Constants.AUTHORIZATION, model.toString(), resp);
        return new ResponseEntity<ResultModel>(ResultModel.ok(model), HttpStatus.OK);
    }

    @Authorization
    @RequestMapping(method = RequestMethod.DELETE) 
    public ResponseEntity<ResultModel> logout(@RequestBody Student user) {
        tokenManager.deleteToken(user.getId());
        Cookie cookie =  CookieUtils.getCookieByName(Constants.AUTHORIZATION, req.getCookies());
        CookieUtils.delete(cookie, resp); 
        return new ResponseEntity<ResultModel>(ResultModel.ok(), HttpStatus.OK);
    }
 
}