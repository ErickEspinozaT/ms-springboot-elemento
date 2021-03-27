package com.consulti.elemento.datasource;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DbConfig {

  @Value("${spring.datasource.hikari.idle-timeout}")
  private int idleTimeout;

  @Value("${spring.datasource.hikari.connection-timeout}")
  private int connectionTimeout;

  @Value("${spring.datasource.hikari.maximum-pool-size}")
  private int maxPoolSize;

  @Value("${spring.datasource.hikari.minimum-idle}")
  private int minPoolSize;

  @Value("${spring.datasource.hikari.max-lifetime}")
  private int maxLifetime;

  private final Map<String, Object> hibernateProperties() {
    Map<String, Object> hibernateProperties = new LinkedHashMap<String, Object>();
    hibernateProperties.put("hibernate.connection.release_mode", "auto");
    return hibernateProperties;
  }

  @Primary
  @Bean(name = "dsElemento")
  public HikariDataSource dsElemento(@Qualifier("dsElementoProperties") HikariConfig dataSourceConfig) {
    return new HikariDataSource(dataSourceConfig);
  }

  @Primary
  @Bean(name = "dsElementoProperties")
  public HikariConfig dsElementoConfig() throws Exception {
    HikariConfig dataSourceConfig = new HikariConfig();
    /*
     * Set: - poolname - username - password - jdbc
     */
    dataSourceConfig.setConnectionTimeout(connectionTimeout);
    dataSourceConfig.setIdleTimeout(idleTimeout);
    dataSourceConfig.setMaximumPoolSize(maxPoolSize);
    dataSourceConfig.setMinimumIdle(minPoolSize);
    dataSourceConfig.setMaxLifetime(maxLifetime);
    dataSourceConfig.setValidationTimeout(10000);
    dataSourceConfig.addDataSourceProperty("oracle.jdbc.timezoneAsRegion", "false");
    return dataSourceConfig;
  }

  @Primary
  @Bean(name = "jdbcElemento")
  @Autowired
  public JdbcTemplate jdbcElementoTemplate(@Qualifier("dsElemento") DataSource dsElemento) {
    return new JdbcTemplate(dsElemento);
  }

  @Primary
  @Bean(name = "ElementoEMFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryMonitoreo(EntityManagerFactoryBuilder builder,
      @Qualifier("dsElemento") DataSource dsElemento) {
    return builder.dataSource(dsElemento).properties(hibernateProperties()).packages("com.consulti.elemento.entity")
        .persistenceUnit("dbElemento").build();
  }

  @Primary
  @Bean(name = "ElementoTM")
  public PlatformTransactionManager transactionManagerMonitoreo(
      @Qualifier("ElementoEMFactory") EntityManagerFactory ElementoEMFactory) {
    return new JpaTransactionManager(ElementoEMFactory);
  }
}