package com.sorz.multidatasourcespring.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class WritingDatasourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.writing")
    public DataSourceProperties writingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "writingDataSource")
    public DataSource writingDataSource() {
        return writingDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
