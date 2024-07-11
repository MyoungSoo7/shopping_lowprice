package com.lms.springcore.aop;

import com.lms.springcore.model.ApiUseTime;
import com.lms.springcore.model.Users;
import com.lms.springcore.repository.ApiUseTimeRepository;
import com.lms.springcore.security.UserDetailsImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UseTimeAop {
    private static final Logger logger = LoggerFactory.getLogger(UseTimeAop.class);
    private final ApiUseTimeRepository apiUseTimeRepository;

    public UseTimeAop(ApiUseTimeRepository apiUseTimeRepository) {
        this.apiUseTimeRepository = apiUseTimeRepository;
    }

    @Around("execution(public * com.lms.springcore.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            Object output = joinPoint.proceed();
            return output;
        } finally {
            // 로그인 회원이 없는 경우, 수행시간 기록하지 않음
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
                // 로그인 회원 정보
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                Users loginUser = userDetails.getUser();
                ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser)
                        .orElse(null);

                long endTime = System.currentTimeMillis();
                long runTime = endTime - startTime;
                if (apiUseTime == null) {
                    apiUseTime = new ApiUseTime(loginUser, runTime);
                } else {
                    apiUseTime.addUseTime(runTime);
                }

                logger.info("API 사용시간 기록: " + loginUser.getUsername() + ", Total Time: " + apiUseTime.getTotalTime() + " ms") ;
                // API 사용시간 및 DB 에 기록
                apiUseTimeRepository.save(apiUseTime);
            }
        }
    }
}
