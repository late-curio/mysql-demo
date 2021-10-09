package com.newrelic.mysqldemo;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ReportGenerator {

    private final BufferedWriter writer;
    private final BufferedWriter systemWriter;
    private String note;
    private String productVersion;
    private boolean agentLoaded = false;

    public void setNote(String note) { this.note = note; }

    public ReportGenerator() throws IOException {
        long ts = System.currentTimeMillis();
        String filename = ts + "-report.csv";
        String systemLog = ts + "-system.csv";
        writer = new BufferedWriter(new FileWriter("/Users/tcrone/temp/mysql/" + filename));
        systemWriter = new BufferedWriter(new FileWriter("/Users/tcrone/temp/mysql/" + systemLog));
        writer.write("timestamp,duration,productVersion,note\n");
        systemWriter.write("timestamp,total,free,used,productVersion,note\n");
        this.initialize();
    }

    public void initialize() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> jvmArgs = runtimeMXBean.getInputArguments();
        System.out.println("JVM arguments:");
        for (String arg : jvmArgs) {
            if (arg.startsWith("-agentpath") || arg.startsWith("-agentlib") || arg.startsWith("-javaagent")) {
                System.out.print("***** ");
                System.out.print(arg);
                System.out.println(" *****");
                agentLoaded = true;
            }
            else {
                System.out.println(arg);
            }
        }
    }

    public void log(long time, long duration, String productVersion) throws IOException {
        if(this.productVersion == null) {
            this.productVersion = productVersion;
        }
        writer.append(String.valueOf(time))
                .append(",")
                .append(String.valueOf(duration))
                .append(",")
                .append(productVersion)
                .append(",")
                .append(note)
                .append("\n");
    }

    public void logMemory(long time, long total, long free, long used) throws IOException {
        System.out.println("Used memory is " + used);
        systemWriter.append(String.valueOf(time))
                .append(",")
                .append(String.valueOf(total))
                .append(",")
                .append(String.valueOf(free))
                .append(",")
                .append(String.valueOf(used))
                .append(",")
                .append(productVersion)
                .append(",")
                .append(note)
                .append("\n");
    }

    public void startSystemLogging() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            long ts = System.currentTimeMillis();
            long total = Runtime.getRuntime().totalMemory();
            long free = Runtime.getRuntime().freeMemory();
            long used = total - free;
            try {
                logMemory(ts, total, free, used);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void close() throws IOException {
        writer.close();
        systemWriter.close();
    }
}
