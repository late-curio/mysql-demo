package com.newrelic.mysqldemo;

public class DoEet implements Runnable {
    private final MyService service;

    public DoEet(MyService service) {
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            this.service.doThings();
        }
    }
}
