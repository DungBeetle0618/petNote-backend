package com.petnote.api.test.mapper;

import com.petnote.api.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDAO {
    User findUserByUserId(String id);

}
