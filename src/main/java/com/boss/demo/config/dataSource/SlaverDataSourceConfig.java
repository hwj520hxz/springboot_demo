package com.boss.demo.config.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author 创建人：何伟杰
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/1/10
 * @time 创建时间：16:42
 */
@Configuration
@PropertySource(value = "classpath:application.yml")
@MapperScan(basePackages = SlaverDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "SlaverDataSource1SqlSessionFactory")
public class SlaverDataSourceConfig {
    static final String PACKAGE = "com.boss.demo.mapper.slaverDataSource";
    static final String SLAVER_LOCATION = "classpath:mapper/slaverDataSource/*.xml";
    @Value("${spring.datasource.slaver.url}")
    private String url;
    @Value("${spring.datasource.slaver.username}")
    private String username;
    @Value("${spring.datasource.slaver.password}")
    private String password;
    @Value("${spring.datasource.slaver.driver-class-name}")
    private String driverClass;

    @Bean(name = "SlaverDataSource")
    @Primary
    public DataSource SlaverDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        return dataSource;
    }

    //事务管理器
    @Bean(name = "SlaverDataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager DataSource1TransactionManager(){
        return new DataSourceTransactionManager(SlaverDataSource());
    }

    //工厂
    @Bean(name = "SlaverDataSource1SqlSessionFactory")
    @Primary
    public SqlSessionFactory dataSource1SqlSessionFactory(@Qualifier("SlaverDataSource") DataSource slaverDataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(slaverDataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SlaverDataSourceConfig.SLAVER_LOCATION));
        return sqlSessionFactory.getObject();
    }
    //想要使用sqlSessionTemplate，需要重写bean，否则会找不到sqlSessionTemplate
    @Bean(name = "slaverSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SlaverDataSource1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

}
