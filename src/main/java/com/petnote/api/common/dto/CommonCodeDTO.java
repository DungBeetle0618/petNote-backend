package com.petnote.api.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CommonCodeDTO extends BaseDTO {
    private String codeGroup1;
    private String codeGroup2;
    private String code;
    private String korName;
    private String engName;
    private int sortSeq;
    private String useYn;
}
