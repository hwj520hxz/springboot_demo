package com.boss.demo.service.user.impl;

import com.boss.demo.commons.exception.BusinessException;
import com.boss.demo.entity.User;
import com.boss.demo.mapper.masterDataSource.UserMapper;
import com.boss.demo.mapper.slaverDataSource.User2Mapper;
import com.boss.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author 创建人：何伟杰
 * @version 版本号：V1.0
 * @ClassName 类名：UserService
 * @Description 功能说明：
 * @date 创建日期：2020/1/10
 * @time 创建时间：16:15
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private User2Mapper user2Mapper;

    public List<User> queryUser() throws BusinessException {
        try {
            return userMapper.queryUser();
        } catch (Exception e){
            throw new BusinessException("查询出现异常");
        }
    }


    public List<User> queryUserById(String id) throws BusinessException {
        try {
            return userMapper.queryUserById(id);
        } catch (Exception e){
            e.printStackTrace();
            String errMsg = MessageFormat.format("根据主键[{0}]查询出现异常,异常信息为[{1}]",id,e.getMessage());
            throw new BusinessException(errMsg);
        }
    }


    public List<User> queryUser2() {
        return user2Mapper.queryUser();
    }
}
