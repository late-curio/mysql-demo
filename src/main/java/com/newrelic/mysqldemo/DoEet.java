package com.newrelic.mysqldemo;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

public class DoEet implements Runnable {
    private final static AtomicLong nextId = new AtomicLong(1);
    private final RestTemplate restTemplate;
    private final long runnerId;

    public DoEet(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.runnerId = nextId.getAndIncrement();
    }

    @Override
    public void run() {
        System.out.println("Starting runner " + runnerId);
        for (int i = 0; i < 1000; i++) {
            try {
                URI uri = new URI("http://localhost:8080");
                restTemplate.getForEntity(uri, String.class);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (i % 10 == 0) {
                System.out.println("Runner " + runnerId + " on iteration " + i);
            }
        }
        System.out.println("Runner " + runnerId + " done.");
    }
}
