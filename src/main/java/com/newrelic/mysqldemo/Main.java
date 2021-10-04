package com.newrelic.mysqldemo;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
//    private final MyService service;
//
//    private final ThreadPoolExecutor executor;
//
//    public MyRestController(MyService service) {
//        this.service = service;
//        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
//    }
//
//    @GetMapping
//    public String getIt() {
//        long time = service.doThings();
//        return Long.toString(time);
//    }
//
//    @GetMapping("/execute")
//    public String execute() {
//        for (int i = 0; i < 100; i++) {
//            executor.execute(new DoEet(service));
//        }
//        return "Running 100 tasks";
//    }
//
}
