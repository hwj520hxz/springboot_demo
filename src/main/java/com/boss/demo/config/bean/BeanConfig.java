package com.boss.demo.config.bean;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：bean管理
 */
@Configuration
public class BeanConfig {

    /**
     * @Description: 当ApplicationContext读如所有的Bean配置信息后，这个类将扫描上下文
     * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。加入这项配置能解决这个bug
     **/
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setUsePrefix(true);
        return creator;
    }
}
