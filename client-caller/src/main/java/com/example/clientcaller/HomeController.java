package com.example.clientcaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private IngredientService service;

    @Autowired
    public HomeController(IngredientService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String home(){
        return service.getIngredientById();
    }
}
