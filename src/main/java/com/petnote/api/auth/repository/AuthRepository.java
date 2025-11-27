package com.petnote.api.auth.repository;

import com.petnote.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, String> {
    @Query("UPDATE UserEntity u SET u.deleteYn = 'Y' WHERE u.userId = :userId")
    void deleteAccount(String userId);
}
