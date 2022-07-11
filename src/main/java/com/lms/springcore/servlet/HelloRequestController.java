package com.lms.springcore.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hello/request")
public class HelloRequestController {
    @GetMapping("/form/html")
    public String helloForm() {
        return "hello-request-form";
    }

    // [Request sample]
// GET http://localhost:8080/hello/request/star/BTS/age/28
    @GetMapping("/star/{name}/age/{age}")
    @ResponseBody
    public String helloRequestPath(@PathVariable String name, @PathVariable int age)
    {
        return String.format("Hello, @PathVariable.<br> name = %s, age = %d", name, age);
    }

    // [Request sample]
// GET http://localhost:8080/hello/request/form/param?name=BTS&age=28
    @GetMapping("/form/param")
    @ResponseBody
    public String helloGetRequestParam(@RequestParam String name, @RequestParam int age) {
        return String.format("Hello, @RequestParam.<br> name = %s, age = %d", name, age);
    }

    // [Request sample]
// POST http://localhost:8080/hello/request/form/param
// Header
// Content type: application/x-www-form-urlencoded
// Body
// name=BTS&age=28
    @PostMapping("/form/param")
    @ResponseBody
    public String helloPostRequestParam(@RequestParam String name, @RequestParam int age) {
        return String.format("Hello, @RequestParam.<br> name = %s, age = %d", name, age);
    }

    // [Request sample]
// POST http://localhost:8080/hello/request/form/model
// Header
// Content type: application/x-www-form-urlencoded
// Body
// name=BTS&age=28
    @PostMapping("/form/model")
    @ResponseBody
    public String helloRequestBodyForm(@ModelAttribute Star star) {
        return String.format("Hello, @RequestBody.<br> (name = %s, age = %d) ", star.name, star.age);
    }

    // [Request sample]
// POST http://localhost:8080/hello/request/form/json
// Header
// Content type: application/json
// Body
// {"name":"BTS","age":"28"}
    @PostMapping("/form/json")
    @ResponseBody
    public String helloPostRequestJson(@RequestBody Star star) {
        return String.format("Hello, @RequestBody.<br> (name = %s, age = %d) ", star.name, star.age);
    }
}
