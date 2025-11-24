package com.petnote.api.common.service.impl;

import com.petnote.api.common.dto.CommonCodeDTO;
import com.petnote.api.common.mapper.CommonCodeMapper;
import com.petnote.api.common.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CommonCodeServiceImpl implements CommonCodeService {
    private final CommonCodeMapper commonCodeMapper;

    /**
     * 공통코드 조회
     * @param dto
     * @return
     */
    @Override
    public CommonCodeDTO selectCommonCode(CommonCodeDTO dto) {
        return commonCodeMapper.selectCommonCode(dto);
    }

    /**
     * 공통코드 리스트 조회
     * @param dto
     * @return
     */
    @Override
    public List<CommonCodeDTO> selectCommonCodeList(CommonCodeDTO dto) {
        return commonCodeMapper.selectCommonCodeList(dto);
    }
}
