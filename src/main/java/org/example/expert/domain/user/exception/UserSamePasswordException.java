package org.example.expert.domain.user.exception;

import lombok.Getter;
import org.example.expert.domain.common.exception.InvalidRequestException;

@Getter
public class UserSamePasswordException extends InvalidRequestException {

    private final int errorCode = UserExceptionMessage.USER_PASSWORD_SAME.getErrorCode();

    public UserSamePasswordException() {
        super(UserExceptionMessage.USER_PASSWORD_SAME.getErrorMessage());
    }
}
