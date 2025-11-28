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

    @Override
    public List<PetDTO> selectList(String userId) {
        return petMapper.selectList(userId);
    }

    @Override
    public void insertPet(PetDTO petDTO) {
        petMapper.insertPet(petDTO);
    }
}
