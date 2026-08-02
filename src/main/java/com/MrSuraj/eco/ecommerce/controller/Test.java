package com.MrSuraj.eco.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/ok")
    public String test(){
        return "It Working fine!!!";
    }
}
