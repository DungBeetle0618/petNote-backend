package com.petnote.api.user.service;

import com.petnote.api.user.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUserByUserId(String userId);
    Optional<UserEntity> getUserByEmail(String email);
    Optional<UserEntity> getUserByUserIdAndEmail(String userId, String email);

    int updateTempPasswordByUserIdAndEmail(String userId, String email, String password);
}
