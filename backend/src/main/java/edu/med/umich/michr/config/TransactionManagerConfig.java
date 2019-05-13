package edu.med.umich.michr.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;

@Configuration
@EnableTransactionManagement
@ComponentScan
public class TransactionManagerConfig {
  @Bean(initMethod = "init", destroyMethod = "close")
  public UserTransactionManager transactionManager(){
     return new UserTransactionManager();
  }

  @Bean
  public UserTransactionImp userTransactionManager() throws SystemException {
    UserTransactionImp userTransactionImp = new UserTransactionImp();
    userTransactionImp.setTransactionTimeout(300);
    return userTransactionImp;
  }

  @Bean
  public JtaTransactionManager jtaTransactionManager(UserTransactionManager transactionManager, UserTransactionImp userTransactionManager) {
    return new JtaTransactionManager(userTransactionManager, transactionManager);
  }

}
