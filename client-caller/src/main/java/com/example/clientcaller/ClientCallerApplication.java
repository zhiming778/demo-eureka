package com.example.clientcaller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHystrix
public class ClientCallerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ClientCallerApplication.class, args);
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
//        new Caller(restTemplate).callAnother20Times();
        IngredientService client = new IngredientService(restTemplate);
        client.getIngredientById();
        client.getIngredientById();
        client.getIngredientById();
        client.getIngredientById();
        client.getIngredientById();
        client.getIngredientById();
    }


    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
