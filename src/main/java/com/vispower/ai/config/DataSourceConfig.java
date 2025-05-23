package com.vispower.ai.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    /**
     * MySQL 数据源配置（主数据源）
     * 用于实时数据查询：车辆通行、事件记录等
     */
    @Bean(name = "mysqlDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * ClickHouse 数据源配置
     * 用于分析查询：统计分析、历史数据等
     */
    @Bean(name = "clickhouseDataSource")
    @ConfigurationProperties("spring.datasource.clickhouse")
    public DataSource clickhouseDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * MySQL JdbcTemplate - 主要的JdbcTemplate
     */
    @Bean(name = "mysqlJdbcTemplate")
    @Primary
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        // 设置查询超时时间（秒）
        jdbcTemplate.setQueryTimeout(30);
        // 设置最大返回行数
        jdbcTemplate.setMaxRows(1000);
        return jdbcTemplate;
    }

    /**
     * ClickHouse JdbcTemplate - 用于分析查询
     */
    @Bean(name = "clickhouseJdbcTemplate")
    public JdbcTemplate clickhouseJdbcTemplate(@Qualifier("clickhouseDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        // ClickHouse 查询配置
        jdbcTemplate.setQueryTimeout(60); // ClickHouse查询可能更耗时
        jdbcTemplate.setMaxRows(10000);   // 分析查询可能需要更多数据
        return jdbcTemplate;
    }

    /**
     * MySQL 事务管理器
     */
    @Bean(name = "mysqlTransactionManager")
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * ClickHouse 事务管理器（ClickHouse 不支持事务，但为了一致性）
     */
    @Bean(name = "clickhouseTransactionManager")
    public PlatformTransactionManager clickhouseTransactionManager(@Qualifier("clickhouseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}