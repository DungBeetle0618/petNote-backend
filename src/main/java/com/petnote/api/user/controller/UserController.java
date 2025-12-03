package com.petnote.api.user.controller;

import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> userFindById(@PathVariable String userId) {
        Optional<UserEntity> user = userService.getUserByUserId(userId);
        if(user.isPresent()){
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("user", user.get());
            return ResponseEntity.ok().body(userMap);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
