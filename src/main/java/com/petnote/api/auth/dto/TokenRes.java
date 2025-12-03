package com.petnote.api.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRes {
    private String accessToken;

    public TokenRes(String access) {
        accessToken = access;
    }
}


