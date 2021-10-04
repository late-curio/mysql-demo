package com.newrelic.mysqldemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
    private final MyService service;

    public MyRestController(MyService service) {
        this.service = service;
    }

    @GetMapping
    public String getIt() {
        long time = service.doThings();
        return Long.toString(time);
    }
}
