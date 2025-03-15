package com.sorz.multidatasourcespring.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.sorz.multidatasourcespring.repository.writing",
        entityManagerFactoryRef = "writingEntityManagerFactory",
        transactionManagerRef = "writingTransactionManager"
)
public class WritingDataSourceJPAConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean writingEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("writingDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sorz.multidatasourcespring.model.writing")
                .persistenceUnit("writing")
                .build();
    }

    @Bean
    public PlatformTransactionManager writingTransactionManager(
            @Qualifier("writingEntityManagerFactory") EntityManagerFactory writingEntityManagerFactory) {
        return new JpaTransactionManager(writingEntityManagerFactory);
    }
}
