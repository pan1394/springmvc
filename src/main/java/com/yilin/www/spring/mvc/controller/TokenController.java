package com.yilin.www.spring.mvc.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yilin.www.spring.entity.Student;
import com.yilin.www.spring.mvc.config.ResultStatus;
import com.yilin.www.spring.mvc.model.ResultModel;
import com.yilin.www.spring.mvc.utils.Constants;
import com.yilin.www.spring.mvc.utils.CookieUtils;
import com.yilin.www.spring.service.StudentService;
import com.yilin.www.spring.token2.Authorization;
import com.yilin.www.spring.token2.CurrentUser;
import com.yilin.www.spring.token2.TokenManager;
import com.yilin.www.spring.token2.TokenModel;
 

/**
 *  
 * @author ScienJus
 * @date 2015/7/30.
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired 
    private StudentService userRepository;

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

        Student user = userRepository.getStudent((userId));
        if (user == null  //閺堫亝鏁為崘锟�
                //!user.getPassword().equals(password)
                //  user.size() != 1
        		) {  //鐎靛棛鐖滈柨娆掝嚖
            //閹绘劗銇氶悽銊﹀煕閸氬秵鍨ㄧ�靛棛鐖滈柨娆掝嚖
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        //閻㈢喐鍨氭稉锟芥稉鐚紀ken閿涘奔绻氱�涙鏁ら幋椋庢瑜版洜濮搁幀锟�
        TokenModel model = tokenManager.createToken(userId);
        CookieUtils.saveAsSimpleCookie(Constants.AUTHORIZATION, model.toString(), resp);
        return new ResponseEntity<ResultModel>(ResultModel.ok(model), HttpStatus.OK);
    }

    @Authorization
    @RequestMapping(method = RequestMethod.DELETE) 
    public ResponseEntity<ResultModel> logout(@CurrentUser Student user, @CookieValue(Constants.AUTHORIZATION) Cookie cookie2) {
    	logger.info("current user, id {}, name {}", user.getId(), user.getName());
        tokenManager.deleteToken(user.getId());
        Cookie cookie =  CookieUtils.getCookieByName(Constants.AUTHORIZATION, req.getCookies());
        logger.info(cookie == cookie2 ? "Same cookie": "I got a different one.");
        TokenModel m = tokenManager.getToken(cookie2.getValue());
        CookieUtils.delete(cookie, resp); 
        return new ResponseEntity<ResultModel>(ResultModel.ok(m), HttpStatus.OK);
    }
 
}