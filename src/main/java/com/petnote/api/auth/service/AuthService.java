package com.petnote.api.auth.service;

import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.dto.SignupDTO;
import com.petnote.global.exception.PetNoteException;

import java.util.Optional;

public interface AuthService {
    boolean signup(SignupDTO dto) throws PetNoteException;
    LoginDTO login(LoginDTO dto);
    void deleteAccount(String userId) throws PetNoteException;

    void setRefreshToken(String userId, String refreshToken);
    String getRefreshToken(String userId);
}
