package com.sorz.multidatasourcespring.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.sorz.multidatasourcespring.repository.reading",
        entityManagerFactoryRef = "readingEntityManagerFactory",
        transactionManagerRef = "readingTransactionManager"
)
public class ReadingDataSourceJPAConfig {
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean readingEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("readingDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sorz.multidatasourcespring.model.reading")
                .persistenceUnit("reading")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager readingTransactionManager(
            @Qualifier("readingEntityManagerFactory") EntityManagerFactory readingEntityManagerFactory) {
        return new JpaTransactionManager(readingEntityManagerFactory);
    }
}
