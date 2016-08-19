package com.lip.core.common.monitor;

import org.javasimon.SimonManager;
import org.javasimon.utils.SimonUtils;

import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class PerformanceMonitorTask extends StreamOutputMonitorTask {

    private static final String MESSAGE_FORMAT_STRING = TIMESTAMP_FORMAT + "-Performance monitor timings:\n%2$s";

    public PerformanceMonitorTask(PrintStream outStream) {
        super(outStream);
    }

    @Override
    public Long getPeriodInSeconds() {
        return TimeUnit.SECONDS.convert(15, TimeUnit.MINUTES);
    }

    @Override
    String formatLine() {
        return String.format(MESSAGE_FORMAT_STRING,
                new Date(),
                SimonUtils.simonTreeString(SimonManager.getRootSimon()));
    }

}
