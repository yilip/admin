package com.lip.core.common.monitor;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class RuntimeMonitor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ScheduledExecutorService exec;

    private List<RuntimeMonitorTaskDefinition> taskDefs;

    public RuntimeMonitor() {
        super();
        final ThreadFactoryBuilder tfb = new ThreadFactoryBuilder().setNameFormat("monitor-worker%s");
        exec = MoreExecutors.getExitingScheduledExecutorService(
                new ScheduledThreadPoolExecutor(1, tfb.build()),
                2L, TimeUnit.SECONDS);
    }

    @PostConstruct
    public void init() {
        for(RuntimeMonitorTaskDefinition task : taskDefs) {
            logger.info("Registering scheduled monitor task [{}] to execute every {} seconds", task.getClass(), task.getPeriodInSeconds());
            exec.scheduleAtFixedRate(task.getRunnable(), 0, task.getPeriodInSeconds(), TimeUnit.SECONDS);
        }
    }

    public void setTaskDefs(List<RuntimeMonitorTaskDefinition> taskDefs) {
        this.taskDefs = taskDefs;
    }

    public interface RuntimeMonitorTaskDefinition {

        Runnable getRunnable();

        Long getPeriodInSeconds();

    }
}
