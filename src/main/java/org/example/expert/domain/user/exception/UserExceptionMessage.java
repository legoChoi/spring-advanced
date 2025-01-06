package org.example.expert.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionMessage {

    USER_PASSWORD_SAME(HttpStatus.BAD_REQUEST.value(), "새 비밀번호는 기존 비밀번호와 같을 수 없습니다."),
    ALREADY_EXISTS_USER_EMAIL(HttpStatus.UNAUTHORIZED.value(), "이미 존재하는 이메일입니다."),
    USER_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED.value(), "잘못된 비밀번호입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "User not found");

    private final int errorCode;
    private final String errorMessage;
}
