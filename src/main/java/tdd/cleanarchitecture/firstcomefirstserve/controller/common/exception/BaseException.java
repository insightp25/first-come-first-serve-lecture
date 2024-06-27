package tdd.cleanarchitecture.firstcomefirstserve.controller.common.exception;

import lombok.Getter;
import tdd.cleanarchitecture.firstcomefirstserve.controller.common.ErrorCode;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
