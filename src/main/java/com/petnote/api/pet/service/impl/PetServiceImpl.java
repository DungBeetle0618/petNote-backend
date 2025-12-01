package com.petnote.api.pet.service.impl;

import com.petnote.api.pet.dto.PetDTO;
import com.petnote.api.pet.mapper.PetMapper;
import com.petnote.api.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
