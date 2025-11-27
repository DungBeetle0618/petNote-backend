package com.petnote.api.common.upload;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Log4j2
public class UploadDTO {
    private MultipartFile uploadFile;
    private List<MultipartFile> uploadFiles;
}
