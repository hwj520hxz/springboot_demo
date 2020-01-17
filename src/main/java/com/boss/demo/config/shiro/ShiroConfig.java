package com.boss.demo.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：shiro相关配置
 */
@Configuration
public class ShiroConfig {

    /**
     * @Description: 将自己的验证方式加入容器
     **/
    @Bean
    public Realm realm() {
        return new CustomRealm();
    }


    /**
     * @Description: 权限管理，配置主要是Realm的管理认证
     **/
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager());
        securityManager.setRealm(realm());
        return securityManager;
    }


    /**
     * @Description: Filter工厂，设置对应的过滤条件和跳转条件
     **/
    @Bean
    protected ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置所要拦截的页面
        Map<String, String> map = new HashMap<>();
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //配置shiro默认登录界面地址
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //设置过滤器
        Map<String, Filter> filtersMap = new HashMap<>();
        filtersMap.put("authc", new MyPassThruAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        return shiroFilterFactoryBean;
    }

    /**
     * @Description: 缓存管理器（可以交给securityManager管理）
     **/
    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }


    //开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证，需要配置以下两个bean
    /**
     * @Description: spring自动代理
     * 当ApplicationContext读如所有的Bean配置信息后，这个类将扫描上下文，寻找所有的Advistor(一个Advisor是一个切入点和一个通知的组成)，将这些Advisor应用到所有符合切入点的Bean中
     **/
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * @Description: 开启AOP注解支持
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
