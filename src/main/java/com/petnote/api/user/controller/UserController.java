package com.petnote.api.user.controller;

import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> userFindById(@PathVariable String userId) {
        Optional<UserEntity> user = userService.getUserByUserId(userId);
        if(user.isPresent()){
            return ResponseEntity.ok().body(Map.of("userId", user.get()));
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
