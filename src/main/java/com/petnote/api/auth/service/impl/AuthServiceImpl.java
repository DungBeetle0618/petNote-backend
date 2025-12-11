package com.petnote.api.auth.service.impl;

import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.dto.SignupDTO;
import com.petnote.api.auth.repository.AuthRepository;
import com.petnote.api.auth.service.AuthService;
import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.repository.UserRepository;
import com.petnote.global.exception.PetNoteException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean signup(SignupDTO dto) throws PetNoteException {
        if(dto == null){
            throw PetNoteException.builder().message("처리 중 오류가 발생했습니다.").build();
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
        if(!user.isPresent()){
            return null;
        }

        UserEntity userEntity = user.get();
        if(passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())){
            return userEntity.toLoginDTO();
        }else{
            return null;
        }
    }

    @Override
    public void deleteAccount(String userId) throws PetNoteException {
        authRepository.deleteAccount(userId);
    }

    @Override
    public void setRefreshToken(String userId, String refreshToken) {
        authRepository.setRefreshToken(userId, refreshToken);
    }

    @Override
    public String getRefreshToken(String userId) {
        return authRepository.getRefreshToken(userId);
    }

    private boolean signupValidationCheck(SignupDTO dto) throws PetNoteException {
        String emailRegex = "\\S+@\\S+\\.\\S+";
        String phoneRegex = "^01[0-9]{8,9}$";
        
        // id 체크
        if(dto.getUserId().length() < 4){
            throw PetNoteException.builder().message("아이디는 4자 이상으로 입력해 주세요.").build();
        }
        Optional<UserEntity> user = userRepository.findById(dto.getUserId());
        if(user.isPresent()){
            throw PetNoteException.builder().message("사용 중인 아이디입니다.").build();
        }
        // password 체크
        if(dto.getPassword().length() < 8){
            throw PetNoteException.builder().message("비밀번호는 8자 이상으로 입력해 주세요.").build();
        }
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw PetNoteException.builder().message("비밀번호 확인이 일치하지 않습니다.").build();
        }
        // email 체크
        if(!Pattern.matches(emailRegex, dto.getEmail())){
            throw PetNoteException.builder().message("올바른 이메일을 입력해 주세요.").build();
        }
        // phone 체크
        if(!Pattern.matches(phoneRegex, dto.getPhone())){
            throw PetNoteException.builder().message("올바른 휴대폰 번호를 입력해 주세요.").build();
        }
        return true;

    }
}
