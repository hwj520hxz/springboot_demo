package com.boss.demo.config.mybatis;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：支持读写分离
 */

@Configuration
@MapperScan("com.boss.demo.mapper")
public class MybatisConfig {
    /**
     * @Description: 自动识别使用的数据库类型
     * 在mapper.xml中databaseId的值就是跟这里对应
     * 如果没有databaseId选择则说明该sql适用所有数据库
     **/
    @Bean
    public DatabaseIdProvider databaseIdProvider(){
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("Oracle","oracle");
        properties.setProperty("MySQL","mysql");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
    @Component
    @Intercepts({
            @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
            @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
            @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
    })
    class MasterSlaveAutoRoutingPlugin implements Interceptor {
        private static final String MASTER = "master";
        private static final String SLAVE = "slave";

        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            if (!StringUtil.isEmpty(DynamicDataSourceContextHolder.peek())) return invocation.proceed();
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            try {
                DynamicDataSourceContextHolder.push(SqlCommandType.SELECT == ms.getSqlCommandType() ? SLAVE : MASTER);
                return invocation.proceed();
            } finally {
                DynamicDataSourceContextHolder.clear();
            }
        }

        @Override
        public Object plugin(Object target) {
            return target instanceof Executor ? Plugin.wrap(target, this) : target;
        }

        @Override
        public void setProperties(Properties properties) {
        }
    }
}
