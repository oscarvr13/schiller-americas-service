package com.shiller.americas.config.feign;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class RetrierConfig implements Retryer {

  private int retryMaxAttempt;

  private long retryInterval;

  private int attempt = 1;

  public RetrierConfig(int retryMaxAttempt, Long retryInterval) {
    this.retryMaxAttempt = retryMaxAttempt;
    this.retryInterval = retryInterval;
  }

  @Override
  public void continueOrPropagate(RetryableException e) {
    log.info("Feign retry attempt {} due to {} ", attempt, e.getMessage());

    if(attempt++ == retryMaxAttempt){
      throw e;
    }
    try {
      Thread.sleep(retryInterval);
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public Retryer clone() {
    return new RetrierConfig(2, 2000L);
  }
}
