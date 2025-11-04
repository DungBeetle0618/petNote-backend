package com.petnote.api.auth.dto;

import com.petnote.api.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String userName;
    private String profileImg;
    private String snsType;
    private String snsToken;
    private String accessToken;
    private String refreshToken;

    public UserEntity toUserEntity(){
        return UserEntity.builder()
                .userId(this.userId)
                .password(this.password)
                .email(this.email)
                .phone(this.phone)
                .userName(this.userName)
                .build();
    }

}
