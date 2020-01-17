package com.boss.demo.service.user;

import com.boss.demo.commons.exception.BusinessException;
import com.boss.demo.entity.User;

import java.util.List;

/**
 * @author 创建人：何伟杰
 * @version 版本号：V1.0
 * @ClassName 接口名：IUserService
 * @Description 功能说明：
 * @date 创建日期：2020/1/10
 * @time 创建时间：16:14
 */
public interface IUserService {
    List<User> queryUser() throws BusinessException;

    User queryUserByAcount(String userName, String password) throws BusinessException;

    List<User> queryUserById(String userId) throws BusinessException;

    List<User> queryUser2();
}
