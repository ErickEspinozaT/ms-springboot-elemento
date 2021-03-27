package com.consulti.elemento.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionManagerConfig {
  @Bean(name = "elementoTransactionManager")
  public ChainedTransactionManager biselTransactionManager(
      @Qualifier("ElementoTM") PlatformTransactionManager ElementoTM) {
    return new ChainedTransactionManager(ElementoTM);
  }
}
