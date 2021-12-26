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
        for (int i = 0; i < 40; i++) {
//            System.out.println(getResponse());
            new Thread(new MyRunnlable(restTemplate)).start();
        }
    }

//    @HystrixCommand(fallbackMethod = "getDefaultResponse",
//            commandProperties = {
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "1"),
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "5000"),
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
//            }
//    )
//    public String getResponse() {
//        throw new RuntimeException();
//        return new RestTemplate().getForObject("http://client-worker/home", String.class);
//    }

//    public String getDefaultResponse() {
//        System.out.println("getDefaultResponse:::This is a fallback method.");
//        return "This is a fallback method.";
//    }
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
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "1"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "500"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
    public String getResponse(){
        return restTemplate.getForObject("http://client-worker/home", String.class);
    }

    private String getDefaultResponse(){
        System.out.println("getDefaultResponse:::This is a fallback method.");
        return "This is a fallback method.";
    }
}
