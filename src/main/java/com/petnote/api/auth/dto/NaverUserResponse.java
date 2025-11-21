package com.petnote.api.auth.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverUserResponse {

    private String resultCode;
    private String message;
    private response response;

    @Data
    public static class response {
        private String id;
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
        private String email;
        private String mobile;


    }
}