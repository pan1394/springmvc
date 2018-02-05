package com.yilin.www.spring.token2.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.yilin.www.spring.dao.StudentDao;
import com.yilin.www.spring.entity.Student;
import com.yilin.www.spring.mvc.utils.Constants;
import com.yilin.www.spring.token2.CurrentUser;

 
/**
 *  
 * @author ScienJus
 * @date 2015/7/31.
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    @Qualifier("studentDao2")
    private StudentDao userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { 
        if (parameter.getParameterType().isAssignableFrom(Student.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Long currentUserId = (Long) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            return userRepository.getStudent(currentUserId).get(0);
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}