package com.petnote.api.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class Test {

    @GetMapping("/hello")
    public String test(){
        log.info("환영");
        return "petNote";
    }
}
