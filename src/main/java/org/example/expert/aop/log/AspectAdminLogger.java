package org.example.expert.aop.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.aop.log.dto.AdminLog;
import org.example.expert.config.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

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
        AdminLog adminLog = createAdminLog();
        String body = getRequestBody(adminLog.getMethod());
        adminLog.setBody(body);

        buildLog("---->", adminLog);
    }

    @AfterReturning(value = "inAdminControllers()", returning = "response")
    public void logAdminControllerAfter(Object response) throws IOException {
        AdminLog adminLog = createAdminLog();
        String body = getResponseBody(response);
        adminLog.setBody(body);

        buildLog("<----", adminLog);
    }

    /**
     * create AdminLog DTO
     */
    private AdminLog createAdminLog() {
        HttpServletRequest request = getServletRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String bearerJwt = request.getHeader("Authorization");
        String id = getUserId(bearerJwt);

        return new AdminLog(id, method, url);
    }

    private String getRequestBody(String method) throws IOException {
        if ("GET".equalsIgnoreCase(method)) { // GET 요청에선 request body 읽기 제외
            return null;
        }

        ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) getServletRequest();
        return objectMapper.readTree(wrapper.getContentAsByteArray()).toString();
    }

    // TODO : response 값 읽기
    private String getResponseBody(Object response) throws IOException {
        return objectMapper.writeValueAsString(response);
    }

    /**
     * 로그 출력
     */
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
}
