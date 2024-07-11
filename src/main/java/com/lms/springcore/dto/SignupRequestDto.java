package com.lms.springcore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";

}