package com.example.clientcaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Caller {

    private RestTemplate restTemplate;

    @Autowired
    public Caller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void callAnother20Times() {
        for (int i = 0; i < 20; i++) {
            new Thread(new MyRunnlable(restTemplate)).start();
        }
    }
}

class MyRunnlable implements Runnable {

    RestTemplate restTemplate;

    public MyRunnlable(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        String response = restTemplate.getForObject("http://client-worker/home", String.class);
        System.out.println(response);
    }
}
