package com.boss.demo.config.webMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    Interceptor interceptor;

    /**
     * @Description: 跨域配置
     * 跨域校验的步骤：发起“预检”请求。
     * 1.根据方法isCorsRequest(request)判断是否为跨域请求；
     * 2.根据方法responseHasCors判断是否包含Access-Control-Allow-Origin这个响应头
     * 3.根据方法isSameOrigin判断发送请求的套接字（IP+端口）与接收请求的是否一致（此处不对比协议）
     * 4.判断是否存在config（类型为CorsConfiguration）
     * CorsRegistry包含类型为CorsConfiguration，所以配置一下信息即可实现跨域问题解决
     **/
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")  //跨域路径
                .allowedHeaders("*")  //允许跨域的请求头
                .allowedMethods("*")  //允许跨域的http方法
                .allowedOrigins("*")  //允许跨域的请求源
                .allowCredentials(true);  //是否允许携带cookies
    }

    /**
     * @Description: 拦截器配置
     **/
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(interceptor)  //添加拦截器
                .addPathPatterns("/**");  //拦截路径
    }

    /**
     * @Description: 解决No mapping for GET /swagger-ui.html
     * 产生原因：spring boot 自动配置并没有将 swagger 的静态资源路径映射到META-INF/resources/ 下面。我们需要自己增加映射
     **/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }




}
