package com.petnote.api.common.upload;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;



@Getter
@Setter
@ToString
@EqualsAndHashCode
@Log4j2
public class FileInfoDTO {
    private String filePath;        //파일주소
    private String originFileName;  //원본파일명
    private String fileName;        //서버저장이름
    private long fileSize;          //파일사이즈
    private String insertDate;      //입력일시
    private String updateDate;      //수정일시
}
