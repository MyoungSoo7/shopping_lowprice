//package com.lms.springcore.servlet;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class LoginController {
//
//    // Login 을 위한 HTML 파일 내려주기
//    @GetMapping("/login")
//    public String loginPage() {
//        return "redirect:/login-form.html";
//    }
//
//    // Login 처리
//    @PostMapping("/login")
//    public String loginProcess(
//            @RequestParam String id,
//            @RequestParam String password,
//            Model model
//    ) {
//        if (id.equals(password)) {
//            model.addAttribute("loginId", id);
//        }
//
//        return "login-result";
//    }
//}
