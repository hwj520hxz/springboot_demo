package com.boss.demo.controller;

import com.boss.demo.commons.data.ResponseResult;
import com.boss.demo.entity.User;
import com.boss.demo.service.user.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */
@RestController
public class LoginController {

    @Autowired
    private IUserService userService;


    @PostMapping(value = "/login")
    @CrossOrigin
    public ResponseResult login(String username, String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return ResponseResult.error();
        }catch (LockedAccountException e) {
            return ResponseResult.error();
        } catch (DisabledAccountException e) {
            return ResponseResult.error();
        }
        User user = (User) subject.getPrincipal();
        subject.getSession().setAttribute("user", user);
        return ResponseResult.success(user);
    }
}
