package com.petnote.api.auth.repository;

import com.petnote.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, String> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE UserEntity u SET u.deleteYn = 'Y' WHERE u.userId = :userId")
    void deleteAccount(String userId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE UserEntity u SET u.refreshToken = :refreshToken WHERE u.userId = :userId")
    void setRefreshToken(String userId, String refreshToken);
    @Query("SELECT u.refreshToken FROM UserEntity u WHERE u.userId = :userId")
    String getRefreshToken(String userId);
}
