package com.petnote.api.common.service;

import com.petnote.api.common.dto.CommonCodeDTO;

import java.util.List;

public interface CommonCodeService {
    CommonCodeDTO selectCommonCode(CommonCodeDTO dto);
    List<CommonCodeDTO> selectCommonCodeList(CommonCodeDTO dto);
}
