package org.example.expert.aop.log.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminLog {
    private String id;
    private String method;
    private String url;
    private String body;
    private LocalDateTime timestamp = LocalDateTime.now();

    public AdminLog(String id, String method, String url) {
        this.id = id;
        this.method = method;
        this.url = url;
    }

    public void setBody(String body) {
        this.body = body;
    }
}