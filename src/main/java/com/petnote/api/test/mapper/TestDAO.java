package com.petnote.api.test.mapper;

import com.petnote.api.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDAO {
    UserEntity findUserByUserId(String id);

}
