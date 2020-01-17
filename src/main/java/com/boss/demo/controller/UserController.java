package com.boss.demo.controller;

import com.boss.demo.commons.data.ResponseResult;
import com.boss.demo.commons.exception.BusinessException;
import com.boss.demo.entity.User;
import com.boss.demo.service.user.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 创建人：何伟杰
 * @version 版本号：V1.0
 * @ClassName 类名：UserController
 * @Description 功能说明：
 * @date 创建日期：2020/1/10
 * @time 创建时间：15:46
 */

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;


    @ApiOperation(value = "查询用户",notes = "查询用户信息",httpMethod = "GET")
    @GetMapping(value = "/queryUser")
//    @CrossOrigin
    public ResponseResult queryUser(Integer pageNum,Integer pageSize){
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<User> userList = userService.queryUser();
            PageInfo<User> pageInfo = new PageInfo<>(userList);
            return ResponseResult.success(pageInfo);
        } catch (BusinessException e){
            return ResponseResult.error();
        }
    }

    @ApiOperation(value = "根据ID查询用户",notes = "根据ID查询用户信息",httpMethod = "GET")
    @GetMapping(value = "/queryUserById")
    public List<User> queryUserById(String userId){
        return userService.queryUserById(userId);
    }

    @GetMapping(value = "/queryUser2")
    public List<User> queryUser2(){
        return userService.queryUser2();
    }




}
