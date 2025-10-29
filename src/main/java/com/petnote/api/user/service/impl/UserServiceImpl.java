package com.petnote.api.user.service.impl;

import com.petnote.api.user.repository.UserRepository;
import com.petnote.api.user.entity.User;
import com.petnote.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findById(userId);
    }
}
