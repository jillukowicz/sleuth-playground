package com.exploration.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * @author jillukowicz
 */
@RestController
public class SleuthTestingController {

  private static final Logger LOG = LoggerFactory.getLogger(SleuthTestingController.class);

  @Autowired
  private SleuthLoggingService sleuthLoggingService;
  @Autowired
  private BeanFactory beanFactory;

  @RequestMapping(value = "/same-span", method = RequestMethod.GET)
  public void simple() {
    LOG.info("Simple span");
    sleuthLoggingService.execute();
  }

  @RequestMapping(value = "/manual-new-span", method = RequestMethod.GET)
  public void manualNewSpan() {
    LOG.info("Manual new span");
    sleuthLoggingService.executeNewSpan();
  }

  @RequestMapping(value = "/thread-new-span", method = RequestMethod.GET)
  public void threadNewSpan() {
    LOG.info("Thread new span");
    Runnable runnable = () -> {
      LOG.info("New thread span work");
    };
    new LazyTraceExecutor(beanFactory, newScheduledThreadPool(1))
        .execute(runnable);
  }

}
