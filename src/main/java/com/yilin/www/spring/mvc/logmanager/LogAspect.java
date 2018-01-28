package com.yilin.www.spring.mvc.logmanager;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import com.yilin.www.spring.mvc.utils.CommonUtils;
import com.yilin.www.spring.mvc.utils.UUIDUtils;

/**
 * ϵͳ��־������
 * @author lin.r.x
 *
 */
@Aspect
@Component
public class LogAspect {
    private  static  final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
    private static final ThreadLocal<Log> logThreadLocal =  new NamedThreadLocal<Log>("ThreadLocal log");

//    private static final ThreadLocal<User> currentUser=new NamedThreadLocal<>("ThreadLocal user");

    @Autowired(required=false) 
    private HttpServletRequest request;

//    @Autowired
//    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
//
//    @Autowired
//    private LogService logService;

    /**
     * Controller���е� ע������
     */
    @Pointcut("@annotation(com.yilin.www.spring.mvc.logmanager.SystemControllerLog)")
    public void controllerAspect(){}

    /**
     * ǰ��֪ͨ ��������Controller���¼�û��Ĳ����Ŀ�ʼʱ��
     * @param joinPoint �е�
     * @throws InterruptedException 
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);//�̰߳󶨱�����������ֻ�е�ǰ������߳̿ɼ���  
        if (logger.isDebugEnabled()){//������־����Ϊdebug
            logger.debug("Clock starts: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(beginTime), request.getRequestURI());
        }

        //��ȡsession�е��û� 
//        HttpSession session = request.getSession();       
//        User user = (User) session.getAttribute("ims_user");    
//        currentUser.set(user);

    }

    /**
     * ����֪ͨ ��������Controller���¼�û��Ĳ���
     * @param joinPoint �е�
     */ 
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
//        User user = currentUser.get();
//        if(user !=null)
        {
            String title="";
            String type="info";                       //��־����(info:���,error:����)
            String remoteAddr=request.getRemoteAddr();//�����IP
            String requestUri=request.getRequestURI();//�����Uri
            String method=request.getMethod();        //����ķ�������(post/get)
            Map<String,String[]> params=request.getParameterMap(); //�����ύ�Ĳ���

            try {
                title=getControllerMethodDescription2(joinPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }    
            // ��ӡJVM��Ϣ��
            long beginTime = beginTimeThreadLocal.get().getTime();//�õ��̰߳󶨵ľֲ���������ʼʱ�䣩  
            long endTime = System.currentTimeMillis();  //2������ʱ��  
            if (logger.isDebugEnabled()){
                logger.debug("Clock ends��{}  URI: {}  Time comsumes�� {}s   Max Memory(-Xmx): {}m  Occupied Memory: {}m  Free Memory from Occupied: {}m  Max Available Memory: {}m",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime), 
                        request.getRequestURI(), 
                        CommonUtils.timePeriod(endTime - beginTime), 
                        Runtime.getRuntime().maxMemory()/1024/1024, 
                        Runtime.getRuntime().totalMemory()/1024/1024, 
                        Runtime.getRuntime().freeMemory()/1024/1024, 
                        (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024); 
            }

            Log log=new Log();
            log.setLogId(UUIDUtils.get());
            log.setTitle(title);
            log.setType(type);
            log.setRemoteAddr(remoteAddr);
            log.setRequestUri(requestUri);
            log.setMethod(method);
            log.setMapToParams(params);
//            log.setUserId(user.getId());
            Date operateDate=beginTimeThreadLocal.get();
            log.setOperateDate(operateDate);
            log.setTimeout(CommonUtils.timePeriod(endTime - beginTime));

            //1.ֱ��ִ�б������
            //this.logService.createSystemLog(log);

            //2.�Ż�:�첽������־
            //new SaveLogThread(log, logService).start();

            //3.���Ż�:ͨ���̳߳���ִ����־����
//            threadPoolTaskExecutor.execute(new SaveLogThread(log, logService));
            logThreadLocal.set(log);
        }

    }

    /**
     *  �쳣֪ͨ ��¼����������־
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")  
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = logThreadLocal.get();
        log.setType("error");
        log.setException(e.toString());
//        new UpdateLogThread(log, logService).start();
    }

    /**
     * ��ȡע���жԷ�����������Ϣ ����service��ע��
     * @param joinPoint�е�
     * @return discription
     */
/*    public static String getServiceMthodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemServiceLog serviceLog = method.getAnnotation(SystemServiceLog.class);
        String discription = serviceLog.description();
        return discription;
    }*/

    /**
     * ��ȡע���жԷ�����������Ϣ ����Controller��ע��
     * 
     * @param joinPoint �е�
     * @return discription
     */
    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemControllerLog controllerLog = method.getAnnotation(SystemControllerLog.class);
        String discription = controllerLog.description();
        return discription;
    }

    /**
     * ������־�߳�
     */
  /*  private static class SaveLogThread implements Runnable {
        private Log log;
        private LogService logService;

        public SaveLogThread(Log log, LogService logService) {
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.createLog(log);
        }
    }*/

    /**
     * ��־�����߳�
     */
    /*private static class UpdateLogThread extends Thread {
        private Log log;
        private LogService logService;

        public UpdateLogThread(Log log, LogService logService) {
            super(UpdateLogThread.class.getSimpleName());
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            this.logService.updateLog(log);
        }
    }*/
}
