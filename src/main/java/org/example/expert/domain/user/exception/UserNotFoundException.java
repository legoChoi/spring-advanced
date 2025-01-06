package org.example.expert.domain.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends IllegalArgumentException {

    private final int errorCode = UserExceptionMessage.USER_NOT_FOUND.getErrorCode();

    public UserNotFoundException() {
        super(UserExceptionMessage.USER_NOT_FOUND.getErrorMessage());
    }
}
