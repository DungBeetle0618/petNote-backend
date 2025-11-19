package com.petnote.api.auth.service.impl;

import com.petnote.api.auth.dto.KakaoUserResponse;
import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.service.SocialLoginService;
import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.service.UserService;
import com.petnote.global.exception.PetNoteException;
import com.petnote.global.utill.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {
    private final WebClient.Builder webClientBuilder;
    private final UserService userService;

    @Override
    public LoginDTO kakaoLogin(String accessToken) throws Exception {

        KakaoUserResponse userInfo = webClientBuilder.build()
            .get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(KakaoUserResponse.class)
            .block();

        if(userInfo == null) {
            throw PetNoteException.builder().message("잠시 후 다시 시도해 주세요.").build();
        }

        log.info("kakao userInfo = {}", userInfo);

        KakaoUserResponse.KakaoAccount kakaoAccount = userInfo.getKakaoAccount();
        KakaoUserResponse.Properties properties = userInfo.getProperties();
        KakaoUserResponse.KakaoAccount.Profile profile = kakaoAccount.getProfile();
        UserEntity user = UserEntity.builder()
                .userId(userInfo.getId())
                .email(kakaoAccount.getEmail())
                .nickname(
                        CommonUtil.nvl(
                                profile.getNickname(),
                                properties.getNickname()
                        )
                )
                .profileImg(
                        CommonUtil.nvl(
                                profile.getProfileImageUrl(),
                                properties.getProfileImage()
                        )
                )
                .build();

        Optional<UserEntity> serverUser = userService.getUserByUserId(user.getUserId());
        // kakao id로 등록된 유저 있다면 로그인 처리
        if(serverUser.isPresent()) {
            return LoginDTO.builder()
                        .userId(serverUser.get().getUserId())
                        .nickName(serverUser.get().getNickname())
                        .build();

        // 없으면 가입 처리
        }else{
            if(user.getNickname() == null) user.setNickname(CommonUtil.randomUpper6());

            Optional<UserEntity> nickNameCheck = Optional.empty();
                while(nickNameCheck.isEmpty()){
                    nickNameCheck = userService.getUserByNickname(user.getNickname());
                    if(nickNameCheck.isPresent()) user.setNickname(CommonUtil.randomUpper6());
                }
            serverUser = userService.save(user);
            if(serverUser.isPresent()) {
                return LoginDTO.builder()
                        .userId(serverUser.get().getUserId())
                        .nickName(serverUser.get().getNickname())
                        .build();
            }else{
                return null;
            }

        }

    }
}
