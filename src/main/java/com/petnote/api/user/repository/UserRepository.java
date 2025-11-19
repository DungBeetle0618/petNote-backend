package com.petnote.api.user.repository;

import com.petnote.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByUserIdAndEmail(String userId, String email);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.userId = :userId AND u.email = :email")
    int updateTempPasswordByUserIdAndEmail(String userId, String email, String password);
}
