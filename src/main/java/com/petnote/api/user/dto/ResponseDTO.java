package com.petnote.api.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDTO {
    private String message;
    private boolean status;
    private String code;
}
