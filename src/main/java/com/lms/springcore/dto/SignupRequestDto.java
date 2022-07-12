package com.lms.springcore.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";

    @Override
    public String toString() {
        return "SignupRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                ", adminToken='" + adminToken + '\'' +
                '}';
    }
}