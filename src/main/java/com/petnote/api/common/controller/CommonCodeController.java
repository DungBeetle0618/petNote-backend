package com.petnote.api.common.controller;

import com.petnote.api.common.dto.CommonCodeDTO;
import com.petnote.api.common.service.CommonCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/code")
    public Map<String, Object> selectForAxios(@RequestBody CommonCodeDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        List<CommonCodeDTO> list = commonCodeService.selectCommonCode(dto);

        resultMap.put("result", "SUCCESS");
        resultMap.put("list", list);
        resultMap.put("page", dto.getPage());
        resultMap.put("records", dto.getRecords());
        resultMap.put("total", dto.getTotal());

        return resultMap;
    }

}
