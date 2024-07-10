package com.lms.springcore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lms.springcore.dto.SignupRequestDto;
import com.lms.springcore.exception.ErrorMessage;
import com.lms.springcore.model.Users;
import com.lms.springcore.service.KakaoUserService;
import com.lms.springcore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto , Model model) {
        try {
            Users user = userService.registerUser(requestDto);
            return "redirect:/user/login";
        } catch (ErrorMessage e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}


