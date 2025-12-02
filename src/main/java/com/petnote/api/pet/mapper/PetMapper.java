package com.petnote.api.pet.mapper;

import com.petnote.api.pet.dto.PetDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetMapper {

    List<PetDTO> selectList(String userId);

    void insertPet(PetDTO petDTO);

    Object selectPet(PetDTO petDTO);

    void updatePet(PetDTO petDTO);
}
