package com.consulti.elemento.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "ElementoEMFactory", transactionManagerRef = "ElementoTM", basePackages = {
    "com.consulti.elemento.repository" })
@EntityScan(basePackages = { "com.consulti.elemento.entity" })
public class DatasourceConfig {
}
