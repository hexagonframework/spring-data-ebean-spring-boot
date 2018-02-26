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

import java.lang.annotation.Annotation;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.ebean.repository.config.EbeanRepositoryConfigExtension;
import org.springframework.data.ebean.repository.config.EnableEbeanRepositories;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * Used to auto-configure Spring Data Ebean Repositories.
 *
 * @author Xuegui Yuan
 */
public class EbeanRepositoriesAutoConfigureRegistrar
    extends AbstractRepositoryConfigurationSourceSupport {

  @Override
  protected Class<? extends Annotation> getAnnotation() {
    return EnableEbeanRepositories.class;
  }

  @Override
  protected Class<?> getConfiguration() {
    return EnableEbeanRepositoriesConfiguration.class;
  }

  @Override
  protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
    return new EbeanRepositoryConfigExtension();
  }

  @EnableEbeanRepositories
  private static class EnableEbeanRepositoriesConfiguration {

  }
}
