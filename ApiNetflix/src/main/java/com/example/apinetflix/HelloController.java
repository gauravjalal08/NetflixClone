package com.example.apinetflix;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
        private String Sayhello(){
        return" welcome to the nexflix Api";
    }
}
