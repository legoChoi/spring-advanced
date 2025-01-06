package org.example.expert.domain.user.exception;

import lombok.Getter;

@Getter
public class UserSamePasswordException extends RuntimeException {

    private final int errorCode = UserExceptionMessage.USER_PASSWORD_SAME.getErrorCode();

    public UserSamePasswordException() {
        super(UserExceptionMessage.USER_PASSWORD_SAME.getErrorMessage());
    }
}
