package com.petnote.api.common.repository;

import com.petnote.api.common.dto.CommonCodeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCodeRepository {
    List<CommonCodeDTO> selectCommonCodeList(CommonCodeDTO dto);

    CommonCodeDTO selectCommonCode(CommonCodeDTO dto);
}
