package com.yilin.www.spring.mvc.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yilin.www.spring.token2.interceptor.AuthorizationInterceptor;
import com.yilin.www.spring.token2.interceptor.CurrentUserMethodArgumentResolver;


/**
 * 配置类，增加自定义拦截器和解析器
 * @see com.yilin.www.spring.token2.interceptor.scienjus.authorization.resolvers.CurrentUserMethodArgumentResolver
 * @see com.scienjus.authorization.interceptor.AuthorizationInterceptor
 * @author ScienJus
 * @date 2015/7/30.
 */
//@Configuration
//@EnableWebMvc 
public class MvcConfig implements WebMvcConfigurer   {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	 
   /* @Autowired
    private AuthorizationInterceptor authorizationInterceptor;*/

 /*   @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;
*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	logger.info("loaded interceptors: " );
        registry.addInterceptor(new AuthorizationInterceptor() ).addPathPatterns("/spring/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	logger.info("loaded argumentResolvers: " );
    	argumentResolvers.add(new CurrentUserMethodArgumentResolver()); 
    }
}