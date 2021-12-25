package com.example.clientcaller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

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
        String response = getResponse();
        System.out.println(response);
    }

    @HystrixCommand(fallbackMethod ="getDefaultResponse",
        commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "3000"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
        })
    private String getResponse(){
        return restTemplate.getForObject("http://client-worker/home", String.class);
    }

    private String getDefaultResponse(){
        return "This is a fallback method.";
    }
}
