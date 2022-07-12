//package com.lms.springcore.servlet;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//@RequestMapping("/hello/rest")
//public class HelloRestController {
//
//    @GetMapping("/json/string")
//    public String helloHtmlString() {
//        return "<html><body>Hello @ResponseBody</body></html>";
//    }
//    @GetMapping("/json/list")
//    public List<String> helloJson() {
//        List<String> words = Arrays.asList("Hello", "Controller", "And", "JSON");
//
//        return words;
//    }
//}
