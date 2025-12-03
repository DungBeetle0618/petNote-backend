package com.petnote.api.community.controller;

import com.petnote.api.common.upload.FileInfoDTO;
import com.petnote.api.common.upload.FileUploadManager;
import com.petnote.api.common.upload.UploadType;
import com.petnote.api.community.dto.CommunityDTO;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommunityController {

    private final FileUploadManager fileUploadManager;

    @PostMapping(value ="/community/upload")
    @ResponseBody
    public String userUpload(@ModelAttribute CommunityDTO dto, HttpServletRequest request){

        log.info("contents=={}", dto.getContents());

        List<FileInfoDTO> fileInfoList = new ArrayList<>();

        if(dto.getUploadFiles() != null && dto.getUploadFiles().size() > 0){
            for(MultipartFile file : dto.getUploadFiles()){
                FileInfoDTO fileInfoVO = fileUploadManager.upload(UploadType.COMMUNITY, file, request);
                log.info("fileInfoVO=={}", fileInfoVO);
                fileInfoList.add(fileInfoVO);
            }
        }


        return "user";
    }


}
