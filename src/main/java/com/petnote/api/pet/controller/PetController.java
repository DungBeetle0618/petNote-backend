package com.petnote.api.pet.controller;

import com.petnote.api.auth.jwt.JwtProvider;
import com.petnote.api.common.upload.FileInfoDTO;
import com.petnote.api.common.upload.FileUploadManager;
import com.petnote.api.common.upload.UploadType;
import com.petnote.api.pet.dto.PetDTO;
import com.petnote.api.pet.service.PetService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pet")
public class PetController {
    private final PetService petService;
    private final JwtProvider jwtProvider;
    private final FileUploadManager fileUploadManager;

    /**
     * 펫리스트 조회
     * @return
     */
    @GetMapping("/listForAxios")
    public Map<String, Object> listForAxios(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        String userId = "";
        if(refreshToken != null){
             userId = jwtProvider.parseToken(refreshToken).getBody().getSubject();
        }

        Map<String, Object> resultMap = new HashMap<>();
        List<PetDTO> list = petService.selectList(userId);

        resultMap.put("result", "SUCCESS");
        resultMap.put("list", list);

        return resultMap;
    }

    /**
     * 펫 등록
     * @param petDTO
     * @return
     */
    @PostMapping("/insertForAxios")
    public Map<String, Object> insertForAxios(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                              @ModelAttribute PetDTO petDTO, HttpServletRequest request) {
        String userId = "";
        if(refreshToken != null){
            userId = jwtProvider.parseToken(refreshToken).getBody().getSubject();
        }
        petDTO.setUserId(userId);
        //펫 정보 등록
        petService.insertPet(petDTO);
        int petNo = petDTO.getPetNo(); //인서트 후 펫 고유번호
        log.info("insert pet no==> " + petNo);

        if(petDTO.getUploadFile() != null){
            FileInfoDTO fileInfoDTO = fileUploadManager.upload(UploadType.ANIMAL, petDTO.getUploadFile(), request);
            log.info("fileInfoVO=={}", fileInfoDTO);

            petService.insertImage(fileInfoDTO, petNo, "Y");
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "SUCCESS");
        
        return resultMap;
    }

    /**
     * 펫 상세 조회
     * @param petDTO
     * @return
     */
    @GetMapping("/selectForAxios")
    public Map<String, Object> selectForAxios(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                              PetDTO petDTO) {
        String userId = "";
        if(refreshToken != null){
            userId = jwtProvider.parseToken(refreshToken).getBody().getSubject();
        }

        Map<String, Object> resultMap = new HashMap<>();
        petDTO.setUserId(userId);

        resultMap.put("result", "SUCCESS");
        resultMap.put("data", petService.selectPet(petDTO));
        return resultMap;
    }

    /**
     * 펫정보 수정
     * @param refreshToken
     * @param petDTO
     * @return
     */
    @PostMapping("/updateForAxios")
    public Map<String, Object> updateForAxios(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                              @ModelAttribute PetDTO petDTO, HttpServletRequest request) {
        String userId = "";
        if(refreshToken != null){
            userId = jwtProvider.parseToken(refreshToken).getBody().getSubject();
        }
        petDTO.setUserId(userId);

        petService.updatePet(petDTO);

        if(petDTO.getUploadFile() != null){
            FileInfoDTO fileInfoDTO = fileUploadManager.upload(UploadType.ANIMAL, petDTO.getUploadFile(), request);
            log.info("fileInfoVO=={}", fileInfoDTO);

            petService.deleteImage(petDTO.getPetNo());
            petService.insertImage(fileInfoDTO, petDTO.getPetNo(), "Y");
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "SUCCESS");

        return resultMap;
    }

}
