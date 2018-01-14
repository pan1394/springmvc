package com.yilin.www.spring.mvc.logmanager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �Զ���ע�� ����Controller
 * 
 * @author lin.r.x
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemControllerLog {
    /**
     * ����ҵ����� ��:Xxx����-ִ��Xxx����
     * @return
     */
    String description() default "";
}
