/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.data.ebean;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.CurrentUserProvider;
import io.ebean.config.ServerConfig;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.ebean.querychannel.EbeanQueryChannelService;
import org.springframework.data.ebean.repository.EbeanRepository;
import org.springframework.data.ebean.repository.config.EbeanRepositoryConfigExtension;
import org.springframework.data.ebean.repository.support.EbeanRepositoryFactoryBean;

/**
 * Auto-configuration for Spring Data's Ebean Repositories.
 * <p>
 * Activates when there is a bean of type {@link javax.sql.DataSource} configured in the
 * context, the Spring Data Ebean
 * {@link org.springframework.data.ebean.repository.EbeanRepository} type is on the classpath,
 * and there is no other, existing
 * {@link org.springframework.data.ebean.repository.EbeanRepository} configured.
 * <p>
 * Once in effect, the auto-configuration is the equivalent of enabling Ebean repositories
 * using the {@link org.springframework.data.ebean.repository.config.EnableEbeanRepositories}
 * annotation.
 * <p>
 *
 * @author Xuegui Yuan
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@ConditionalOnClass(EbeanRepository.class)
@ConditionalOnMissingBean( {EbeanRepositoryFactoryBean.class,
    EbeanRepositoryConfigExtension.class})
@ConditionalOnProperty(prefix = "spring.data.ebean.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import(EbeanRepositoriesAutoConfigureRegistrar.class)
public class EbeanRepositoriesAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public CurrentUserProvider emptyCurrentUserProvider() {
    return new CurrentUserProvider() {
      public Object currentUser() {
        return "";
      }
    };
  }

  @SuppressWarnings("SpringJavaAutowiringInspection")
  @Bean
  public ServerConfig defaultEbeanServerConfig(DataSource dataSource, CurrentUserProvider currentUserProvider) {
    ServerConfig config = new ServerConfig();

    config.setDataSource(dataSource);
    config.setExternalTransactionManager(new SpringJdbcTransactionManager());

    config.loadFromProperties();
    config.setDefaultServer(true);
    config.setRegister(true);
    config.setAutoCommitMode(false);
    config.setExpressionNativeIlike(true);

    config.setCurrentUserProvider(currentUserProvider);

    return config;
  }

  @Bean
  public EbeanServer defaultEbeanServer(ServerConfig defaultEbeanServerConfig) {
    return EbeanServerFactory.create(defaultEbeanServerConfig);
  }

  @Bean
  @ConditionalOnMissingBean
  public EbeanQueryChannelService ebeanQueryChannelService(EbeanServer ebeanServer) {
    return new EbeanQueryChannelService(ebeanServer);
  }
}
