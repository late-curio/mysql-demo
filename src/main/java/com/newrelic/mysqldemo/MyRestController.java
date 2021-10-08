package com.newrelic.mysqldemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class MyRestController {
    private final MyService service;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ThreadPoolExecutor executor;
    private final int threadCount = 100;

    public MyRestController(MyService service) {
        this.service = service;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

    @GetMapping
    public String getIt() throws IOException {
        long time = service.doThings();
        return Long.toString(time);
    }

    @GetMapping("/execute")
    public String execute() {
        for (int i = 0; i < threadCount; i++) {
            executor.execute(new DoEet(restTemplate));
        }
        return "Running 100 tasks";
    }

    @GetMapping("/terminate")
    public String terminate() {
        executor.shutdown();
        return "Terminated";
    }
}
