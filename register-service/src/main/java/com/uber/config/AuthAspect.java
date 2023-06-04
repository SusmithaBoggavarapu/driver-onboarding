package com.uber.config;

import com.uber.client.AuthClient;
import com.uber.common.exception.Errors;
import com.uber.common.exception.UnauthorizedException;
import com.uber.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AuthAspect {

    private static final String INVALID_AUTHORIZATION_MESSAGE = "invalid authorization token";

    private static final String INVALID_USER = "invalid user";


    @Autowired
    private AuthClient authClient;

    @Around(value = "execution(* com.uber.controller.*.*(..)) and args(authToken,..)")
    public ResponseEntity<Response> authServiceValidation(ProceedingJoinPoint joinPoint, String authToken) throws Throwable {
        log.info("auth check for {} with  authToken {} ", joinPoint.getSignature().getName(), authToken);

        if (StringUtils.isEmpty(authToken)) {
            log.info("authorization failed {}  authToken {}", joinPoint.getStaticPart().getSignature().getName(), authToken);
            throw new UnauthorizedException(Errors.UNAUTHORIZED_INVALID_TOKEN);
        }
        return (ResponseEntity<Response>)  joinPoint.proceed();

    }

}