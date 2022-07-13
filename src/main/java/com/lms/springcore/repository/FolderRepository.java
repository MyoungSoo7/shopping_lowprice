package com.lms.springcore.repository;

import com.lms.springcore.model.Folder;
import com.lms.springcore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(Users user);
    List<Folder> findAllByUserAndNameIn(Users user, List<String> names);
}
