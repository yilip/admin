package com.lip.core.common.monitor;

import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class MemoryHeapUsageMonitorTask extends StreamOutputMonitorTask {

    private static final float BYTES_PER_MB = 1000 * 1000;
    private static final String MESSAGE_FORMAT_STRING = TIMESTAMP_FORMAT + "_JVM heap usage estimate [%2$.1fM used | %3$.1fM free | %4$.1fM total | %5$.1fM max]";

    public MemoryHeapUsageMonitorTask(PrintStream outStream) {
        super(outStream);
    }
    
    @Override
    public Long getPeriodInSeconds() {
        return TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);
    }

    @Override
    String formatLine() {
        final Runtime rt = Runtime.getRuntime();
        return String.format(MESSAGE_FORMAT_STRING,
                new Date(),
                (rt.totalMemory() - rt.freeMemory())/BYTES_PER_MB,
                rt.freeMemory()/BYTES_PER_MB,
                rt.totalMemory()/BYTES_PER_MB,
                rt.maxMemory()/BYTES_PER_MB);
    }

}
