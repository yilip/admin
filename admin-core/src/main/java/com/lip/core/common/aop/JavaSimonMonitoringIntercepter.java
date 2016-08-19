package com.lip.core.common.aop;

import java.io.Serializable;
import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.javasimon.jmx.SimonManagerMXBean;
import org.javasimon.jmx.SimonManagerMXBeanImpl;
import org.javasimon.source.MonitorSource;
import org.javasimon.source.StopwatchTemplate;
import org.javasimon.spring.SpringStopwatchSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class JavaSimonMonitoringIntercepter implements MethodInterceptor, Serializable {
    private static final String PERF_MESSAGE_TEMPLATE = "Method execution time: {}";

    public static enum LogLevel{
        INFO, DEBUG, TRACE
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StopwatchTemplate<MethodInvocation> stopwatchTemplate;

    private LogLevel logLevel = LogLevel.INFO;

    public JavaSimonMonitoringIntercepter() {
        this(new SpringStopwatchSource(SimonManager.manager()).cache());
    }

    public JavaSimonMonitoringIntercepter(Manager manager) {
        this(new SpringStopwatchSource(manager).cache());
    }

    public JavaSimonMonitoringIntercepter(MonitorSource<MethodInvocation, Stopwatch> stopwatchSource) {
        this.stopwatchTemplate = new StopwatchTemplate<MethodInvocation>(stopwatchSource);
    }

    @PostConstruct
    public void init() {
        registerMbeans();
    }

    public void destroy() throws Exception {
        unregisterMbeans();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Split split = stopwatchTemplate.start(invocation);
        try{
            return invocation.proceed();
        }
        finally {
            stopwatchTemplate.stop(split);
            logPerformance(split, logLevel);
        }
    }

    private void logPerformance(final Split split, final LogLevel level) {
        switch(level) {
        case INFO:
            if(logger.isInfoEnabled()) {
                logger.info(PERF_MESSAGE_TEMPLATE, split.toString());
            }
            break;
        case DEBUG:
            if(logger.isDebugEnabled()) {
                logger.debug(PERF_MESSAGE_TEMPLATE, split.toString());
            }
            break;
        case TRACE:
        default:
            if(logger.isTraceEnabled()) {
                logger.trace(PERF_MESSAGE_TEMPLATE, split.toString());
            }
            break;
        }
    }

    private void registerMbeans() {
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try{
            final ObjectName name = new ObjectName("JavaSimon (performance):type=Monitoring");
            if(mbs.isRegistered(name)) {
                mbs.unregisterMBean(name);
            }
            final SimonManagerMXBean simon = new SimonManagerMXBeanImpl(SimonManager.manager());
            mbs.registerMBean(simon, name);
            logger.info("JavaSimon monitoring JMX bean has been explicitly registered under name {}", name);
        }
        catch(Exception e) {
            logger.warn("JavaSimon monitoring JMX bean registration failed: " + e.getMessage(), e);
        }
    }

    private void unregisterMbeans() {
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try{
            final ObjectName name = new ObjectName("JavaSimon (performance):type=Monitoring");
            if(mbs.isRegistered(name)) {
                mbs.unregisterMBean(name);
            }
            logger.info("JavaSimon monitoring JMX bean has been explicitly unregistered");
        }
        catch(Exception e) {
            logger.warn("JavaSimon monitoring JMX bean unregistration failed: " + e.getMessage(), e);
        }
    }

    public void setLogLevel(LogLevel level) {
        this.logLevel = level;
    }
}
