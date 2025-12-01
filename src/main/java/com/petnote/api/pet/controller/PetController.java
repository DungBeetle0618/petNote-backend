package com.petnote.api.pet.controller;

import com.petnote.api.pet.dto.PetDTO;
import com.petnote.api.pet.service.PetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 펫리스트 조회
     * @return
     */
    @GetMapping("/listForAxios")
    public Map<String, Object> listForAxios() {
        String userId = "aaaa";

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
    public Map<String, Object> insertForAxios(@RequestBody PetDTO petDTO) {
        String userId = "aaaa";
        petDTO.setUserId(userId);

        petService.insertPet(petDTO);

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
    public Map<String, Object> selectForAxios(PetDTO petDTO) {
        String userId = "aaaa";
        Map<String, Object> resultMap = new HashMap<>();
        petDTO.setUserId(userId);

        resultMap.put("result", "SUCCESS");
        resultMap.put("data", petService.selectPet(petDTO));
        return resultMap;
    }

}
