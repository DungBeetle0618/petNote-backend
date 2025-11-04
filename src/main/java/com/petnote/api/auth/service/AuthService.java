package com.petnote.api.auth.service;

import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.dto.SignupDTO;

import java.util.Optional;

public interface AuthService {
    boolean signup(SignupDTO dto);
    LoginDTO login(LoginDTO dto);
}
