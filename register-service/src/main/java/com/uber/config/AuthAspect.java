package com.uber.config;

import com.uber.auth.AuthService;
import com.uber.entity.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Aspect
@Slf4j
public class AuthAspect {

    @Autowired
    private AuthService authService;

    private static final String INVALID_AUTHORIZATION_MESSAGE = "invalid authorization token";

    private static final String INVALID_USER = "invalid user";

    private static final int ERROR_CODE = 401;

    @Around(value = "execution(* com.samsclub.supplier.shipnodes.controller.*.*(..)) and args(authToken,..)")
    public ResponseEntity<Response<String>> authServiceValidation(ProceedingJoinPoint joinPoint, String authToken) throws Throwable {
        log.info("auth check for {} with  authToken {} ", joinPoint.getSignature().getName(), authToken);

        if (StringUtils.isEmpty(authToken)) {
            throw new IllegalArgumentException(INVALID_AUTHORIZATION_MESSAGE);
        }
        if (!authService.isValidUser(authToken)) {
            log.info("authorization failed {}  authToken {}", joinPoint.getStaticPart().getSignature().getName(), authToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<>(null, null, new Response.Errors(ERROR_CODE, INVALID_USER)));
        }
        return (ResponseEntity<Response<String>>) joinPoint.proceed();

    }

}