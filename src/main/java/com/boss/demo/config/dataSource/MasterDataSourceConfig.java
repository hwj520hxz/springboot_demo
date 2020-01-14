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
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "MasterDataSource1SqlSessionFactory")
public class MasterDataSourceConfig {
    static final String PACKAGE = "com.boss.demo.mapper.masterDataSource";
    static final String MAPPER_LOCATION = "classpath:mapper/masterDataSource/*.xml";
    @Value("${spring.datasource.run.url}")
    private String url;
    @Value("${spring.datasource.run.username}")
    private String username;
    @Value("${spring.datasource.run.password}")
    private String password;
    @Value("${spring.datasource.run.driver-class-name}")
    private String driverClass;

    @Bean(name = "MasterDataSource")
    @Primary
    public DataSource MasterDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        return dataSource;
    }

    //事务管理器
    @Bean(name = "MasterDataSource1TransactionManager")
    @Primary
    public DataSourceTransactionManager DataSource1TransactionManager(){
        return new DataSourceTransactionManager(MasterDataSource());
    }


    //工厂
    @Bean(name = "MasterDataSource1SqlSessionFactory")
    @Primary
    public SqlSessionFactory dataSource1SqlSessionFactory(@Qualifier("MasterDataSource") DataSource masterDataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(masterDataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        return sqlSessionFactory.getObject();
    }

    //想要使用模板，需要重写bean，否则会找不到sqlSessionTemplate
    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("MasterDataSource1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
}
