package com.github.shoppingmallproject.config;

import com.github.shoppingmallproject.config.properties.DataSourceProperties;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EntityScan
@EnableConfigurationProperties(DataSourceProperties.class)
@RequiredArgsConstructor
@EnableJpaRepositories(
        basePackages = {
                "com.github.shoppingmallproject.repository.userRoles",
                "com.github.shoppingmallproject.repository.users",
                "com.github.shoppingmallproject.repository.product",
                "com.github.shoppingmallproject.repository.cart",
                "com.github.shoppingmallproject.repository.review",
                "com.github.shoppingmallproject.repository.order",
                "com.github.shoppingmallproject.repository.productPhoto",
                "com.github.shoppingmallproject.repository.orderItem",
                "com.github.shoppingmallproject.repository.productOption"
        },
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
        transactionManagerRef = "tm"
)
public class JpaConfig {
    private final DataSourceProperties dataSourceProperties;
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(dataSourceProperties.getUrl());
        driverManagerDataSource.setUsername(dataSourceProperties.getUsername());
        driverManagerDataSource.setPassword(dataSourceProperties.getPassword());
        driverManagerDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        return driverManagerDataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource datasource){
        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
        lemfb.setDataSource(datasource);
        lemfb.setPackagesToScan(
                "com.github.shoppingmallproject.repository.userRoles",
                "com.github.shoppingmallproject.repository.users",
                "com.github.shoppingmallproject.repository.product",
                "com.github.shoppingmallproject.repository.cart",
                "com.github.shoppingmallproject.repository.review",
                "com.github.shoppingmallproject.repository.order",
                "com.github.shoppingmallproject.repository.productPhoto",
                "com.github.shoppingmallproject.repository.orderItem",
                "com.github.shoppingmallproject.repository.productOption"
        );
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        lemfb.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comment", "true");

        lemfb.setJpaPropertyMap(properties);

        return lemfb;
    }
//    @Bean(name = "tm")
//    public PlatformTransactionManager platformTransactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
    @Bean(name = "tm")
    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
         return new JpaTransactionManager(entityManagerFactory);
    }
}
