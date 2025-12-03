package com.petnote.api.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginReq {
    private String userId;
    private String password;
    private String deviceId;
}
