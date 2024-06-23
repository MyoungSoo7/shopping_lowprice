package com.lms.springcore.validator;

import com.lms.springcore.dto.SignupRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public static void validateUserInput(SignupRequestDto user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("사용자명이 없습니다.");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호가 없습니다.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("이메일이 없습니다.");
        }
    }

}
