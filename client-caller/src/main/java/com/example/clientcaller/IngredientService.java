package com.example.clientcaller;

import java.util.ArrayList;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class IngredientService {

    private RestTemplate rest;

    public IngredientService(RestTemplate rest) {
        this.rest = rest;
    }

    @HystrixCommand(fallbackMethod = "getDefaultIngredients",
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "5"),
                    @HystrixProperty(
                            name = "circuitBreaker.requestVolumeThreshold",
                            value = "1"),
                    @HystrixProperty(
                            name = "circuitBreaker.errorThresholdPercentage",
                            value = "50"),
                    @HystrixProperty(
                            name = "metrics.rollingStats.timeInMilliseconds",
                            value = "3000"),
                    @HystrixProperty(
                            name = "circuitBreaker.sleepWindowInMilliseconds",
                            value = "60000")
            })
    public String getIngredientById() {
        return rest.getForObject(
                "http://ingredient-service/ingredients/",
                String.class);
    }

    private String getDefaultIngredients() {
        return "This is a fallback";
    }
}
