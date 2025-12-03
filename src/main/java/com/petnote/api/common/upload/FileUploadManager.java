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
        String root_path = request.getServletContext().getRealPath("/WEB-INF");

        System.out.println("root_path ===================> " + root_path);

        File uploadDir = new File(root_path + sp + typePath.getPath());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        FileInfoDTO fileInfoVO = new FileInfoDTO();
        String file_name = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        String file_path = root_path + sp + typePath.getPath() + sp + file_name;

        System.out.println("file_path ===================> " + file_path);

        try {
            uploadFile.transferTo(new File(file_path));
        }catch(Exception e) {
            e.printStackTrace();
        }

        //파일주소
        fileInfoVO.setFilePath(file_path);
        //원본파일명
        fileInfoVO.setOriginFileName(uploadFile.getOriginalFilename());
        //서버저장이름
        fileInfoVO.setFileName(file_name);
        //파일사이즈
        fileInfoVO.setFileSize(uploadFile.getSize());


        return fileInfoVO;

    }






}
