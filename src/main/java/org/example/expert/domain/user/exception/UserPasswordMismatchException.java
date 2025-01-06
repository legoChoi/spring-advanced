package org.example.expert.domain.user.exception;


import lombok.Getter;
import org.example.expert.domain.common.exception.InvalidRequestException;

@Getter
public class UserPasswordMismatchException extends InvalidRequestException {

    private final int errorCode = UserExceptionMessage.USER_PASSWORD_MISMATCH.getErrorCode();

    public UserPasswordMismatchException() {
        super(UserExceptionMessage.USER_PASSWORD_MISMATCH.getErrorMessage());
    }
}
