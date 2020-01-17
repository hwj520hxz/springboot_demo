package com.boss.demo.config.webMvc;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：拦截器
 */
@Component
public class Interceptor implements HandlerInterceptor {
    NamedThreadLocal<Long> timeStop = new NamedThreadLocal<>("timeStop");
    private static Logger log = LoggerFactory.getLogger(Interceptor.class);
    /**
     * @Description: 在请求处理之前进行调用（Controller方法调用之前）
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        timeStop.set(System.currentTimeMillis());
        return true;
    }

    /**
     * @Description: 在请求处理之后调用，但是在视图被渲染之前（Controller方法调用之后）
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("调用postHandle方法");
    }

    /**
     * @Description: 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * System.getProperty：获取系统变量
     * line.separator：行分隔符
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        StringBuffer requestInfo = new StringBuffer();
        requestInfo.append("\r\n========请求信息Begin========\r\n");
        requestInfo.append("远程IP：" + request.getRemoteAddr() + System.getProperty("line.separator"));
        requestInfo.append("请求地址：" + request.getRequestURI() + System.getProperty("line.separator"));
        requestInfo.append("参数信息：");
        Map<String, String[]> params = request.getParameterMap();
        for(Map.Entry<String, String[]> entry : params.entrySet()){
            requestInfo.append(entry.getKey() + "=");
            if(entry.getValue().length > 1){
                //以逗号分隔进行拼接
                requestInfo.append("[" + StringUtils.join(Arrays.asList(entry.getValue()),',') + "]");
            } else {
                requestInfo.append(StringUtils.join(Arrays.asList(entry.getValue()),','));
            }
            requestInfo.append("，");
        }
        if(params.size() == 0){
            requestInfo.append("无");
        } else {
            requestInfo = new StringBuffer(requestInfo.substring(0, requestInfo.length() - 1));
        }
        //结束时间-得到线程绑定的局部变量（开始时间）
        requestInfo.append(System.getProperty("line.separator")+"耗时：" + (System.currentTimeMillis() - timeStop.get()) + "毫秒"+System.getProperty("line.separator"));
        requestInfo.append("========请求信息End========"+System.getProperty("line.separator"));
        log.info(requestInfo.toString());
        requestInfo.setLength(0);
    }
}
