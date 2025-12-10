package com.petnote.api.pet.service.impl;

import com.petnote.api.common.upload.FileInfoDTO;
import com.petnote.api.pet.dto.PetDTO;
import com.petnote.api.pet.mapper.PetMapper;
import com.petnote.api.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetMapper petMapper;

    /**
     * 펫 리스트 조회
     * @param userId
     * @return
     */
    @Override
    public List<PetDTO> selectList(String userId) {
        return petMapper.selectList(userId);
    }

    /**
     * 펫 등록
     * @param petDTO
     */
    @Override
    public void insertPet(PetDTO petDTO) {
        petMapper.insertPet(petDTO);
    }

    /**
     * 펫 상세 조회
     * @param petDTO
     * @return
     */
    @Override
    public Object selectPet(PetDTO petDTO) {
        return petMapper.selectPet(petDTO);
    }

    /**
     * 펫정보 수정
     * @param petDTO
     */
    @Override
    public void updatePet(PetDTO petDTO) {
        petMapper.updatePet(petDTO);
    }

    /**
     * 펫 이미지 저장
     * @param fileInfoDTO
     * @param petNo
     * @param mainYn
     */
    @Override
    public void insertImage(FileInfoDTO fileInfoDTO, int petNo, String mainYn) {
        Map<String, Object> param = new HashMap<>();
        param.put("file", fileInfoDTO);
        param.put("petNo", petNo);
        param.put("mainYn", mainYn);
        petMapper.insertImage(param);
    }

    /**
     * 대표이미지 삭제
     * @param petNo
     */
    @Override
    public void deleteImage(int petNo) {
        petMapper.deleteImage(petNo);
    }
}
