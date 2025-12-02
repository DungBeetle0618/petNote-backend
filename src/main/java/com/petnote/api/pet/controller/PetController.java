package com.petnote.api.pet.controller;

import com.petnote.api.auth.jwt.JwtProvider;
import com.petnote.api.pet.dto.PetDTO;
import com.petnote.api.pet.service.PetService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtProvider jwtProvider;

    /**
     * 펫리스트 조회
     * @return
     */
    @GetMapping("/listForAxios")
    public Map<String, Object> listForAxios(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " = " + cookie.getValue());
            }
        }


        String userId = "aaaa";
        if(refreshToken != null){
             userId = jwtProvider.parseToken(refreshToken).getBody().getSubject();
            System.out.println("======> " +  userId);
        } else {
            System.out.println("======>리프레시 없음");
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
    public Map<String, Object> insertForAxios(@RequestBody PetDTO petDTO) {
        String userId = "aaaa";
        petDTO.setUserId(userId);

        //TODO: 첫 동물이면 자동으로 대표 설정.
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
