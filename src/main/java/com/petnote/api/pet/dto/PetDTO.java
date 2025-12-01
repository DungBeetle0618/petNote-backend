package com.petnote.api.pet.dto;

import com.petnote.api.common.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class PetDTO extends BaseDTO {
    private int petNo;
    private String userId;
    private String petName;
    private String birth;
    private String birthKnowYn;
    private String age;
    private String gender;
    private String neutrificationYn;
    private String speciesType;
    private String speciesName;
    private String breedType;
    private String breedName;
    private String breed;
    private String remark;
    private String profileImg;
    private String useYn;
    private String bodyLength;
    private String mainYn;
    private String petInfo;
}