package com.petnote.api.user.controller;

import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> userFindById(@AuthenticationPrincipal User user) {
        log.info("============userName = {}", user.getUsername());

        Optional<UserEntity> dbUser = userService.getUserByUserId(user.getUsername());
        if(dbUser.isPresent()){
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("user", dbUser.get());
            return ResponseEntity.ok().body(userMap);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
