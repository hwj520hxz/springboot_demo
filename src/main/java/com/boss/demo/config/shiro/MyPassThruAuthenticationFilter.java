package com.boss.demo.config.shiro;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：跨域问题处理（解决预请求为options被拦截情况），在shiro拦截器处设置
 */
public class MyPassThruAuthenticationFilter extends PassThruAuthenticationFilter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("FilterConfig init");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//           HttpServletRequest request = (HttpServletRequest) servletRequest;
//
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, If-Modified-Since, x-token");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//
//    }

    /**
     * @Description: 给请求加上请求头（可以在前端设置请求头，后端就不需要设置）
     **/
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())
                || httpRequest.getMethod().equals(RequestMethod.GET.name())
                || httpRequest.getMethod().equals(RequestMethod.POST.name())
                || httpRequest.getMethod().equals(RequestMethod.DELETE.name())
                || httpRequest.getMethod().equals(RequestMethod.PUT.name())) {
            httpResponse.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("Origin"));
            httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(HttpStatus.OK.value());
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }


//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletResponse httpResp = WebUtils.toHttp(response);
//        HttpServletRequest httpReq = WebUtils.toHttp(request);
//
//        /** 系统重定向会默认把请求头清空，这里通过拦截器重新设置请求头，解决跨域问题 */
//        httpResp.addHeader("Access-Control-Allow-Origin", httpReq.getHeader("Origin"));
//        httpResp.addHeader("Access-Control-Allow-Headers", "*");
//        httpResp.addHeader("Access-Control-Allow-Methods", "*");
//        httpResp.addHeader("Access-Control-Allow-Credentials", "true");
//
//        if (isLoginRequest(request, response)) {
//            return true;
//        } else {
//            saveRequestAndRedirectToLogin(request, response);
//            return false;
//        }
//    }
}
