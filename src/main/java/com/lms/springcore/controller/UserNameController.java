package com.lms.springcore.controller;

import com.lms.springcore.model.UserName;
import com.lms.springcore.repository.UserNameRepository;
import com.lms.springcore.service.UserNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserNameController {
    private final UserNameService userService;
    private final UserNameRepository userRepository;

    @Autowired
    public UserNameController(UserNameService userService, UserNameRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/create")
    public void createUser() {
        UserName user = userService.createUser();

// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/delete")
    public void deleteUser() {
        UserName user = userService.deleteUser();

// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/update/fail")
    public void updateUserFail() {
        UserName user = userService.updateUserFail();

// 중요!) DB 에 변경 내용이 적용되었는지 확인!
// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/update/1")
    public void updateUser1() {
        UserName user = userService.updateUser1();

// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }

    @GetMapping("/user/update/2")
    public void updateUse2() {
        UserName user = userService.updateUser2();

// 중요!) DB 에 변경 내용이 적용되었는지 확인!
// 테스트 회원 데이터 삭제
        userRepository.delete(user);
    }
}
