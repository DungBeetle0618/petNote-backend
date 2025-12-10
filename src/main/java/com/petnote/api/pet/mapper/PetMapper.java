package com.petnote.api.pet.mapper;

import com.petnote.api.pet.dto.PetDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PetMapper {

    List<PetDTO> selectList(String userId);

    void insertPet(PetDTO petDTO);

    Object selectPet(PetDTO petDTO);

    void updatePet(PetDTO petDTO);

    void insertImage(Map<String, Object> param);

    void deleteImage(int petNo);
}
