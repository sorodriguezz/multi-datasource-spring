package com.sorz.multidatasourcespring.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class ReadingDatasourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.reading")
    public DataSourceProperties readingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "readingDataSource")
    @Primary
    public DataSource readingDataSource() {
        return readingDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
