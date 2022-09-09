package com.example.nplusone.config;


import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class TestDataSourceConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        DataSource realDataSource = properties.initializeDataSourceBuilder().build();
        return ProxyDataSourceBuilder
                .create(realDataSource)
                .countQuery()
//                    .logQueryByCommons(INFO)
//                    .multiline()
                .name("MyDS")
                .build();
    }
}
