package org.example.expert.domain.user.exception;

import lombok.Getter;
import org.example.expert.domain.common.exception.InvalidRequestException;

@Getter
public class UserNotFoundException extends InvalidRequestException {

    private final int errorCode = UserExceptionMessage.USER_NOT_FOUND.getErrorCode();

    public UserNotFoundException() {
        super(UserExceptionMessage.USER_NOT_FOUND.getErrorMessage());
    }
}
