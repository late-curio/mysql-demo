package com.newrelic.mysqldemo;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class DoEet implements Runnable {
    private final RestTemplate restTemplate;

    public DoEet(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        while (true) {
            try {
                URI uri = new URI("http://localhost:8080");
                restTemplate.getForEntity(uri, String.class);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
