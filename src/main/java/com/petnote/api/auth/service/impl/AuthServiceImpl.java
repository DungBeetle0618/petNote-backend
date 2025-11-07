package com.petnote.api.auth.service.impl;

import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.dto.SignupDTO;
import com.petnote.api.auth.repository.AuthRepository;
import com.petnote.api.auth.service.AuthService;
import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean signup(SignupDTO dto) {
        if(dto == null){
            return false;
        }

        if(!signupValidationCheck(dto)){
            return false;
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        authRepository.save(dto.toUserEntity());

        return true;
    }

    @Override
    public LoginDTO login(LoginDTO dto) {
        Optional<UserEntity> user = userRepository.findById(dto.getUserId());
        if(user.isEmpty()){
            return null;
        }

        UserEntity userEntity = user.get();
        if(passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())){
            return userEntity.toLoginDTO();
        }else{
            return null;
        }
    }

    private boolean signupValidationCheck(SignupDTO dto){
        // id 중복 체크
        if(dto.getUserId().isEmpty()){
            return false;
        }
        authRepository.findById(dto.getUserId());

        // password 유효성 체크

        // email 중복 체크

        return true;

    }
}
