package com.lip.core.common.monitor;

import java.io.PrintStream;

abstract class StreamOutputMonitorTask implements RuntimeMonitor.RuntimeMonitorTaskDefinition {

    static final String TIMESTAMP_FORMAT = "%1$td %1$tb %1$tY %1$tH:%1$tM:%1$tS.%1$tL";
    private final PrintStream outStream;

    public StreamOutputMonitorTask(PrintStream outStream) {
        super();
        this.outStream = outStream;
    }

    abstract String formatLine();

    @Override
    public Runnable getRunnable() {
        return new Runnable() {

            @Override
            public void run() {
                outStream.println(formatLine());
                outStream.flush();
            }
        };
    }

}
