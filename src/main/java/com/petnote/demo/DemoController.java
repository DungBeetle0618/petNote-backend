package com.petnote.demo;

import org.springframework.web.bind.annotation.GetMapping;

public class DemoController {

    @GetMapping("/")
    public String home(){
        return "home";
    }


}
