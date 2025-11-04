package com.petnote.api.user.service;

import com.petnote.api.user.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUserByUserId(String userId);
}
