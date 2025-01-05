package org.example.expert.aop.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.aop.log.dto.AdminLog;
import org.example.expert.config.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.logging.Logger;

@Aspect
@Component
@RequiredArgsConstructor
public class AspectAdminLogger {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut("execution(* org.example.expert.domain..controller..*Admin*.*(..))")
    private void inAdminControllers() { }

    @Before("inAdminControllers()")
    public void logAdminControllerBefore() throws IOException {
        AdminLog context = createLogContext();
        String body = getRequestBody(context.getMethod());
        context.setBody(body);

        buildLog("---->", context);
    }

    @After("inAdminControllers()")
    public void logAdminControllerAfter() throws IOException {
        AdminLog context = createLogContext();
        String body = getResponseBody();
        context.setBody(body);

        buildLog("<----", context);
    }

    private AdminLog createLogContext() {
        HttpServletRequest request = getServletRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String bearerJwt = request.getHeader("Authorization");
        String id = getUserId(bearerJwt);

        return new AdminLog(id, method, url);
    }

    private String getRequestBody(String method) throws IOException {
        if ("GET".equalsIgnoreCase(method)) {
            return null;
        }

        ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) getServletRequest();
        return objectMapper.readTree(wrapper.getContentAsByteArray()).toString();
    }

    private String getResponseBody() throws IOException {
        ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper) getServletResponse();
        return objectMapper.readTree(wrapper.getContentAsByteArray()).toString();
    }

    private void buildLog(String tag, AdminLog context) {
        StringBuilder log = new StringBuilder();
        log.append(tag)
                .append("[").append(context.getId()).append("]")
                .append("[").append(context.getTimestamp()).append("]")
                .append("[").append(context.getMethod()).append("]")
                .append("[").append(context.getUrl()).append("]");

        String body = context.getBody();
        if (body != null && !body.isEmpty()) {
            log.append("[Body: ").append(body).append("]");
        }

        logger.info(log.toString());
    }

    private String getUserId(String bearerJwt) {
        String jwt = jwtUtil.substringToken(bearerJwt);
        Claims claims = jwtUtil.extractClaims(jwt);
        return claims.getSubject();
    }

    private HttpServletRequest getServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    private HttpServletResponse getServletResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getResponse();
    }
}
