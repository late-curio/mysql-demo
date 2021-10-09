package com.newrelic.mysqldemo;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ReportGenerator {

    private final static String ROOT_DIR = "/Users/tcrone/temp/mysql/";
    private final static SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final BufferedWriter writer;
    private final BufferedWriter systemWriter;
    private String productVersion;
    private boolean agentLoaded = false;

    public ReportGenerator(MySQLVersionService mySQLVersionService) throws IOException {
        this.checkForAgent();
        long ts = System.currentTimeMillis();
        String filename = "report.csv";
        String systemLog = "system.csv";
        Date date = new Date(ts);
        String dateTimeString = DATE_FMT.format(date);
        String mysqlVersion = mySQLVersionService.getVersion();
        String folder = ROOT_DIR + (agentLoaded ? "AGENT-" : "NO_AGENT-") + mysqlVersion + "-" + dateTimeString;
        System.out.println("*** Creating directory for log files");
        System.out.println("*** " + folder);
        Files.createDirectories(Paths.get(folder));
        System.out.println("*** Done");
        writer = new BufferedWriter(new FileWriter( folder + "/" + filename));
        systemWriter = new BufferedWriter(new FileWriter(folder + "/" + systemLog));
        writer.write("timestamp,duration,productVersion,agent\n");
        systemWriter.write("timestamp,total,free,used,productVersion,agent\n");
    }

    public void checkForAgent() {
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

    private String getAgentValue() {
        return agentLoaded ? "AGENT" : "NO_AGENT";
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
                .append(getAgentValue())
                .append("\n");
    }

    public void logMemory(long time, long total, long free, long used) throws IOException {
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
                .append(getAgentValue())
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
