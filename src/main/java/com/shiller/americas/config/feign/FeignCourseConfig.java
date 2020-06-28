package com.shiller.americas.config.feign;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignCourseConfig {

  //@Bean
  public Retryer retryer() {
    System.out.println("Crea el bean de retryer");
    return new RetrierConfig(2, 2000L);
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    System.out.println("Crea el error decoder");
    return new FeignErrorDecoder();
  }
}
