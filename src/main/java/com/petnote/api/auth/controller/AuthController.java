package com.petnote.api.auth.controller;

import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.dto.SignupDTO;
import com.petnote.api.auth.service.AuthService;
import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<SignupDTO> signup(@RequestBody SignupDTO dto) {
        if(authService.signup(dto)){
            return ResponseEntity.ok().body(dto);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO dto) {
        Optional<UserEntity> user = userService.getUserByUserId(dto.getUserId());
        if(user.isPresent()){
            return ResponseEntity.ok().body(user.get().toLoginDTO());
        }else{
            return ResponseEntity.badRequest().body(null);
        }

    }
}
