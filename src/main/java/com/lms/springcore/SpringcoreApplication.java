package com.lms.springcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//@ServletCo
// mponentScan // @WebServlet 어노테이션이 동작하게 함
@EnableScheduling   // 스프링부트에서 스케줄러가 작동하게함
@EnableJpaAuditing  // 시간 자동변경이 가능하게함
@SpringBootApplication
public class SpringcoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringcoreApplication.class, args);

    }
}
