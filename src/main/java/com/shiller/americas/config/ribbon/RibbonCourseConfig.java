package com.shiller.americas.config.ribbon;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.ZonePreferenceServerListFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class RibbonCourseConfig {

  @Bean
  public IRule loadBlancingRule() {
    return new RetryRule(new WeightedResponseTimeRule(), 500);
    //return new ZoneAvoidanceRule();
  }

  @Bean
  public ZonePreferenceServerListFilter serverListFilter() {
    log.info("Configuring zone");
    ZonePreferenceServerListFilter filter = new ZonePreferenceServerListFilter();
    //filter.setZone("zone2");
    return filter;
  }

  @Bean
  public IPing pingConfiguration(ServerList<Server> servers) {
    String pingPath = "/actuator/health";
    IPing ping = new PingUrl(false, pingPath);
    log.info("Configuring ping URI to [{}]", pingPath);
    return ping;
  }

  @Bean
  public ServerList<Server> serverList() {
    return new ServerList<Server>() {
      @Override
      public List<Server> getUpdatedListOfServers() {
        List<Server> serverList = Arrays.asList(new Server("http", "localhost", 8084), new Server("http", "localhost", 8082), new Server("http", "localhost", 8081));
        //List<Server> serverList = null;
        log.info("Returning updated list of servers [{}]", serverList);
        return serverList;
      }

      @Override
      public List<Server> getInitialListOfServers() {
        return Arrays.asList(new Server("http", "localhost", 8084), new Server("http", "localhost", 8081));
      }
    };
  }

  @Bean
  public IClientConfig ribbonClientConfig() {
    DefaultClientConfigImpl defaultClientConfigImpl =  new DefaultClientConfigImpl();
    System.out.println(defaultClientConfigImpl.getProperties());
    defaultClientConfigImpl.loadDefaultValues();
    System.out.println(defaultClientConfigImpl.getProperties());

    defaultClientConfigImpl.setProperty(CommonClientConfigKey.MaxAutoRetriesNextServer, 1);
    defaultClientConfigImpl.setProperty(CommonClientConfigKey.MaxAutoRetries, 0);

    System.out.println(defaultClientConfigImpl.getProperties());

    return defaultClientConfigImpl;
  }


}
