package org.example.expert.domain.user.exception;


import lombok.Getter;

@Getter
public class UserPasswordMismatchException extends RuntimeException {

    private final int errorCode = UserExceptionMessage.USER_PASSWORD_MISMATCH.getErrorCode();

    public UserPasswordMismatchException() {
        super(UserExceptionMessage.USER_PASSWORD_MISMATCH.getErrorMessage());
    }
}
