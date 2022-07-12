//package com.lms.springcore.servlet;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/hello/response")
//public class HelloResponseController {
//    // [Response header]
//// Location: http://localhost:8080/hello.html
//    @GetMapping("/html/redirect")
//    public String htmlFile() {
//        return "redirect:/hello.html";
//    }
//
//    // [Response header]
//// Content-Type: text/html
//// [Response body]
//// * resources/templates/hello.html 의 text 내용
//// <!DOCTYPE html>
//// <html>
//// <head><meta charset="UTF-8"><title>By Templates engine</title></head>
//// <body>Hello, Spring 정적 웹 페이지!!</body>
//// </html>
//    @GetMapping("/html/templates")
//    public String htmlTemplates() {
//        return "hello";
//    }
//
//    // [Response header]
//// Content-Type: text/html
//// [Response body]
//// <!DOCTYPE html>
//// <html>
//// <head><meta charset="UTF-8"><title>By @ResponseBody</title></head>
//// <body>Hello, Spring 정적 웹 페이지!!</body>
//// </html>
//    @GetMapping("/body/html")
//    @ResponseBody
//    public String helloStringHTML() {
//        return "<!DOCTYPE html>" +
//                "<html>" +
//                "<head><meta charset=\"UTF-8\"><title>By @ResponseBody</title></head>" +
//                "<body> Hello, 정적 웹 페이지!!</body>" +
//                "</html>";
//    }
//
//    // [Response header]
//// Content-Type: text/html
//// [Response body]
//// * resources/templates/hello-visit.html 의 text 내용
//    @GetMapping("/html/dynamic")
//    public String helloHtmlFile(Model model) {
//        visitCount++;
//        model.addAttribute("visits", visitCount);
//// resources/templates/hello-visit.html
//        return "hello-visit";
//    }
//
//    private static long visitCount = 0;
//
//    // [Response header]
//// Content-Type: text/html
//// [Response body]
//// {"name":"BTS","age":28}
//    @GetMapping("/json/string")
//    @ResponseBody
//    public String helloStringJson() {
//        return "{\"name\":\"BTS\",\"age\":28}";
//    }
//
//    // [Response header]
//// Content-Type: application/json
//// [Response body]
//// {"name":"BTS","age":28}
//    @GetMapping("/json/class")
//    @ResponseBody
//    public Star helloJson() {
//        return new Star("BTS", 28);
//    }
//}
