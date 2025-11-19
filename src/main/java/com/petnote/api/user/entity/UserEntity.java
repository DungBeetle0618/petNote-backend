package com.petnote.api.user.entity;

import com.petnote.api.auth.dto.LoginDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "PN_USER")
@Alias("User")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @Size(max = 20)
    @Column(name = "USER_ID", nullable = false, length = 20)
    private String userId;

    @Size(max = 256)
    @Column(name = "PASSWORD", length = 256)
    private String password;

    @Size(max = 30)
    @Column(name = "EMAIL", length = 30)
    private String email;

    @Size(max = 14)
    @Column(name = "PHONE", length = 14)
    private String phone;

    @Size(max = 200)
    @Column(name = "USER_NAME", length = 200)
    private String userName;

    @Size(max = 100)
    @Column(name = "PROFILE_IMG", length = 100)
    private String profileImg;

    @Size(max = 30)
    @Column(name = "NICKNAME", length = 30)
    private String nickname;

    @Column(name = "PASSWORD_UPDATE_DATE")
    private Instant passwordUpdateDate;

    @Column(name = "PASSWORD_FAIL_COUNT")
    private Integer passwordFailCount;

    @Size(max = 20)
    @Column(name = "SNS_TYPE", length = 20)
    private String snsType;

    @Size(max = 256)
    @Column(name = "SNS_TOKEN", length = 256)
    private String snsToken;

    @Column(name = "LOGIN_DATE")
    private Instant loginDate;

    @Size(max = 256)
    @Column(name = "ACCESS_TOKEN", length = 256)
    private String accessToken;

    @Size(max = 256)
    @Column(name = "REFRESH_TOKEN", length = 256)
    private String refreshToken;

    @Column(name = "USE_YN")
    private Character useYn;

    @Column(name = "INSERT_DATE")
    private Instant insertDate;

    @Column(name = "UPDATE_DATE")
    private Instant updateDate;

    public LoginDTO toLoginDTO(){
        return LoginDTO.builder()
                .userId(this.userId)
                .userName(this.userName)
                .email(this.email)
                .build();
    }

}