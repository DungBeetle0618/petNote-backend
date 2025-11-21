package com.petnote.api.user.service.impl;

import com.petnote.api.user.repository.UserRepository;
import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Optional<UserEntity> save(UserEntity user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<UserEntity> getUserByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public Optional<UserEntity> getUserByUserIdAndEmail(String userId, String email) {
        return userRepository.findByUserIdAndEmail(userId, email);
    }

    @Override
    public int updateTempPasswordByUserIdAndEmail(String userId, String email, String password) {
        return userRepository.updateTempPasswordByUserIdAndEmail(userId, email, password);
    }

}
