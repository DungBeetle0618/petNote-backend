package com.petnote.api.pet.service;

import com.petnote.api.common.upload.FileInfoDTO;
import com.petnote.api.pet.dto.PetDTO;

import java.util.List;

public interface PetService {
    List<PetDTO> selectList(String userId);

    void insertPet(PetDTO petDTO);

    Object selectPet(PetDTO petDTO);

    void updatePet(PetDTO petDTO);

    void insertImage(FileInfoDTO fileInfoDTO, int petNo, String mainYn);

    void deleteImage(int petNo);

    void deletePet(PetDTO petDTO);
}
