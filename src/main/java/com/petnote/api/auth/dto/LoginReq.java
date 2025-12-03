package com.petnote.api.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginReq {
    private String userId;
    private String password;
    private String deviceId;
}
