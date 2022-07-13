package com.lms.springcore.controller;

import com.lms.springcore.model.ApiUseTime;
import com.lms.springcore.model.UserRoleEnum;
import com.lms.springcore.repository.ApiUseTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiUseTimeController {
    private ApiUseTimeRepository apiUseTimeRepository;

    @Autowired
    public ApiUseTimeController(ApiUseTimeRepository apiUseTimeRepository) {
        this.apiUseTimeRepository = apiUseTimeRepository;
    }

    @Secured(UserRoleEnum.Authority.ADMIN)
    @GetMapping("/api/use/time")
    public List<ApiUseTime> getAllApiUseTime() {
        return apiUseTimeRepository.findAll();
    }
}