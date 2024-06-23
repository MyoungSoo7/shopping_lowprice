package com.lms.springcore.repository;

import com.lms.springcore.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByKakaoId(Long kakaoId);
    Optional<Users> findByEmail(String email);
}