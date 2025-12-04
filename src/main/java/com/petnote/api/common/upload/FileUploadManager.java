package com.petnote.api.common.upload;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Log4j2
@Service("FileUploadManager")
public class FileUploadManager {

    /**
     * 파일 업로드
     * @param typePath COMMUNITY, ANIMAL 등 업로드 위치 구분
     * @param uploadFile 업로드할 파일 정보(MultipartFile)
     * @return 저장된 파일 정보(FileInfoDTO)
     */
    public FileInfoDTO upload(UploadType typePath, MultipartFile uploadFile, HttpServletRequest request) {

        String sp = File.separator;

        String appRootPath = request.getServletContext().getRealPath("/");
        System.out.println("appRootPath ===================> " + appRootPath);

        File appRootDir = new File(appRootPath);

        File webappsDir = appRootDir.getParentFile();

        File uploadDir = new File(webappsDir, "resources" + sp + typePath.getPath());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String file_name = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();

        File destFile = new File(uploadDir, file_name);
        System.out.println("destFile ===================> " + destFile.getAbsolutePath());

        try {
            uploadFile.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String webPath = "/resources/" + typePath.getPath() + "/" + file_name;

        FileInfoDTO fileInfoDTO = new FileInfoDTO();

        //파일주소
        fileInfoDTO.setFilePath(webPath);
        //원본파일명
        fileInfoDTO.setOriginFileName(uploadFile.getOriginalFilename());
        //서버저장이름
        fileInfoDTO.setFileName(file_name);
        //파일사이즈
        fileInfoDTO.setFileSize(uploadFile.getSize());

        return fileInfoDTO;
    }







}
