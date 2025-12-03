package com.petnote.api.common.controller;

import com.petnote.api.common.dto.CommonCodeDTO;
import com.petnote.api.common.service.CommonCodeService;
import javax.transaction.Transactional;
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
@RequestMapping("/api/common")
public class CommonCodeController {
    private final CommonCodeService commonCodeService;

    /**
     * 공통코드 리스트 조회
     * @param dto
     * @return
     */
    @GetMapping("/selectListForAxios")
    public Map<String, Object> selectListForAxios(CommonCodeDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();

        List<CommonCodeDTO> list = commonCodeService.selectCommonCodeList(dto);
        resultMap.put("result", "SUCCESS");
        resultMap.put("list", list);

        return resultMap;
    }

    /**
     * 공통코드 조회
     * @param dto
     * @return
     */
    @GetMapping("/selectForAxios")
    public Map<String, Object> selectForAxios(CommonCodeDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", "SUCCESS");
        resultMap.put("code", commonCodeService.selectCommonCode(dto));

        return resultMap;
    }

}
