package com.exploration.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

/**
 * @author jillukowicz
 */
@Service
public class SleuthLoggingService {
  private static final Logger LOG = LoggerFactory.getLogger(SleuthLoggingService.class);

  @Autowired
  private Tracer tracer;

  public void execute(){
    LOG.info("Some service work");
  }

  public void executeNewSpan(){
    LOG.info("Some service work");
    Span span = tracer.createSpan("I'm a new span");
    LOG.info("New span work");
    tracer.close(span);
    LOG.info("Work finished");
  }

}
