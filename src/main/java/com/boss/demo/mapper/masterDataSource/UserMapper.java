package com.boss.demo.mapper.masterDataSource;

import com.boss.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 创建人：何伟杰
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/1/10
 * @time 创建时间：15:45
 */
public interface UserMapper {

    List<User> queryUser();

    List<User> queryUserById(@Param("id") String id);
}
