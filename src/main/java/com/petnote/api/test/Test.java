package com.petnote.api.test;

import com.petnote.api.test.mapper.TestDAO;
import com.petnote.api.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class Test {
    private final TestDAO testDAO;

    @GetMapping("/hello")
    public String test(@RequestParam String name){

        UserEntity userEntity = testDAO.findUserByUserId(name);
        if(userEntity == null){
            return "실패";
        }else{
            return "성공";
        }
    }
}
