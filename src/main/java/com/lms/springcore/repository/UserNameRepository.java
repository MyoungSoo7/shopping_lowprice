package com.lms.springcore.repository;

import com.lms.springcore.model.UserName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNameRepository extends JpaRepository<UserName, String>{
    //List<UserName> findByFavoriteFoodContaining(String word);
}


