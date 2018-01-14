package com.yilin.www.spring.mvc.logmanager;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
 

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yilin.www.spring.mvc.utils.StringUtils;

/**
 * ��־��-��¼�û�������Ϊ
 * @author lin.r.x
 *
 */
public class Log implements Serializable{
    private static final long serialVersionUID = 1L;

    private String logId;           //��־����  
    private String type;            //��־����  
    private String title;           //��־����  
    private String remoteAddr;          //�����ַ  
    private String requestUri;          //URI   
    private String method;          //����ʽ  
    private String params;          //�ύ����  
    private String exception;           //�쳣    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operateDate;           //��ʼʱ��  
    private String timeout;         //����ʱ��  
    private String userId;          //�û�ID  

    public String getLogId() {
        return StringUtils.isBlank(logId) ? logId : logId.trim();
    }
    public void setLogId(String logId) {
        this.logId = logId;
    }


    public String getType() {
        return StringUtils.isBlank(type) ? type : type.trim();
    }
    public void setType(String type) {
        this.type = type;
    }


    public String getTitle() {
        return StringUtils.isBlank(title) ? title : title.trim();
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getRemoteAddr() {
        return StringUtils.isBlank(remoteAddr) ? remoteAddr : remoteAddr.trim();
    }
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }


    public String getRequestUri() {
        return StringUtils.isBlank(requestUri) ? requestUri : requestUri.trim();
    }
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }


    public String getMethod() {
        return StringUtils.isBlank(method) ? method : method.trim();
    }
    public void setMethod(String method) {
        this.method = method;
    }


    public String getParams() {
        return StringUtils.isBlank(params) ? params : params.trim();
    }
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * �����������
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        if (paramMap == null){
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        this.params = params.toString();
    }


    public String getException() {
        return StringUtils.isBlank(exception) ? exception : exception.trim();
    }
    public void setException(String exception) {
        this.exception = exception;
    }


    public Date getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }


    public String getTimeout() {
        return StringUtils.isBlank(timeout) ? timeout : timeout.trim();
    }
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }


    public String getUserId() {
        return StringUtils.isBlank(userId) ? userId : userId.trim();
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

}