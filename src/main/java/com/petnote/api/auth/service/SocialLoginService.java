package com.petnote.api.auth.service;

import com.petnote.api.auth.dto.KakaoUserResponse;
import com.petnote.api.auth.dto.LoginDTO;

public interface SocialLoginService {
    LoginDTO kakaoLogin(String accessToken) throws Exception;
}
