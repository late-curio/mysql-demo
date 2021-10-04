package com.newrelic.mysqldemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class MyRestController {
    private final MyService service;

    private final ThreadPoolExecutor executor;
    private final int threadCount = 4;

    public MyRestController(MyService service) {
        this.service = service;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

    @GetMapping
    public String getIt() {
        long time = service.doThings();
        return Long.toString(time);
    }

    @GetMapping("/execute")
    public String execute() {
        for (int i = 0; i < 10; i++) {
            executor.execute(new DoEet(service));
        }
        return "Running 10 tasks";
    }

    @GetMapping("/terminate")
    public String terminate() {
        executor.shutdown();
        return "Terminated";
    }
}
