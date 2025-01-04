package org.example.expert.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.logging.Logger;


@Aspect
@Component
@RequiredArgsConstructor
public class AspectAdminLogger {

    private final JwtUtil jwtUtil;
    private final Logger logger = Logger.getLogger(AspectAdminLogger.class.getName());

    @Pointcut("execution(* org.example.expert.domain..controller..*Admin*.*(..))")
    private void inAdminControllers() { }

    @Before("inAdminControllers()")
    public void logAdminControllerBefore() {
        String log = buildLog();
        logger.info(log);
    }

    private String buildLog() {
        HttpServletRequest request = getServletRequest();
        LocalDateTime time = LocalDateTime.now();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();

        String bearerJwt = request.getHeader("Authorization");
        String id = getUserId(bearerJwt);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(id).append("]");
        sb.append("[").append(time).append("]");
        sb.append("[").append(method).append("]");
        sb.append("[").append(url).append("]");

        return sb.toString();
    }

    private String getUserId(String bearerJwt) {
        String jwt = jwtUtil.substringToken(bearerJwt);
        Claims claims = jwtUtil.extractClaims(jwt);
        return claims.getSubject();
    }

    private HttpServletRequest getServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    private HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getResponse() : null;
    }
}
