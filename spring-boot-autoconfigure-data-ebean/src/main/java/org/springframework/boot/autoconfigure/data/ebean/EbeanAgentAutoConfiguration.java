package org.springframework.boot.autoconfigure.data.ebean;

import org.avaje.agentloader.AgentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

/**
 * @author Xuegui Yuan
 */
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(AgentLoader.class)
public class EbeanAgentAutoConfiguration implements BeanFactoryPostProcessor, PriorityOrdered {
  private static final Logger log = LoggerFactory.getLogger(EbeanAgentAutoConfiguration.class);

  public EbeanAgentAutoConfiguration() {
    load(); // Spring has already evaluated the @ConditionalOnClass
  }

  private static void load() {
    if (!AgentLoader.loadAgentFromClasspath("ebean-agent", "debug=1")) {
      log.debug("ebean-agent not loaded");
    }
  }

  /**
   * Loads the Ebean agent if the agent-loader and the agent itself are present
   * on the classpath, or does nothing otherwise.
   * <p>
   * Do not call this method from a static initializer as this can lead to a JVM
   * deadlock (the agent attach thread will attempt to acquire the class loader
   * lock, which is held during static initialization).
   */
  public static void enable() {
    try {
      load();
    } catch (NoClassDefFoundError e) {
      /* ignored */
    }
  }

  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    // We're not actually doing anything with the BeanFactory, but implementing
    // BeanFactoryPostProcessor ensures we get instantiated early, ideally
    // before anybody has a chance to load any entity classes we want to
    // enhance.
  }

  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
