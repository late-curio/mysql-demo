package com.newrelic.mysqldemo;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class ReportGenerator {

    private final BufferedWriter writer;

    public ReportGenerator() throws IOException {
        String filename = "report-" + System.currentTimeMillis();
        writer = new BufferedWriter(new FileWriter("/Users/tcrone/temp/" + filename));
        writer.write("timestamp,duration\n");
    }

    public void log(long time, long duration) throws IOException {
        writer.append(String.valueOf(time))
                .append(",")
                .append(String.valueOf(duration))
                .append("\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}
