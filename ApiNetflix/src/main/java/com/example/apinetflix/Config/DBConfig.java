package com.example.apinetflix.Config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Value("spring.datasource.url")
    private String jdbcUrl;
    @Value("spring.datasource.username")
    private String username;
    @Value("spring.datasource.password")
    private String password;
    @Bean(destroyMethod= "close")
    @Primary
    DataSource getDataSource(){ //to get connection
        BasicDataSource dataSource= new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

}
