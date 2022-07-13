package com.lms.springcore.repository;

import com.lms.springcore.model.ApiUseTime;
import com.lms.springcore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(Users user);
}
