package com.lms.springcore.service;

import com.lms.springcore.dto.SignupRequestDto;
import com.lms.springcore.exception.ErrorMessage;
import com.lms.springcore.model.UserRoleEnum;
import com.lms.springcore.model.Users;
import com.lms.springcore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Value("${admin.token}")
    private String adminToken;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new ErrorMessage("중복된 사용자 ID가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(adminToken)) {
                throw new ErrorMessage("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
            logger.info("관리자 권한으로 회원가입");
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        Users user = new Users(username, password, email, role);
        userRepository.save(user);

        return user;
    }
}