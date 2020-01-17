package com.boss.demo.config.shiro;

import com.boss.demo.entity.User;
import com.boss.demo.service.user.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(CustomRealm.class);

    @Autowired
    private IUserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入授权过程");
        System.out.println("进入授权过程");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入认证过程");
        System.out.println("进入认证过程");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //获取用户名
        String userName = usernamePasswordToken.getUsername();
        //获取密码
        String password = new String(usernamePasswordToken.getPassword());
        //去数据库查询该用户是否存在
        User user = userService.queryUserByAcount(userName, password);
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确！");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return simpleAuthenticationInfo;
    }
}
