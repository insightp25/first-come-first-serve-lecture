package tdd.cleanarchitecture.firstcomefirstserve.domain.exception;

import tdd.cleanarchitecture.firstcomefirstserve.controller.exception.BaseException;
import tdd.cleanarchitecture.firstcomefirstserve.controller.ErrorCode;

public class SessionUnavailableException extends BaseException {
    public SessionUnavailableException() {
        super(ErrorCode.SESSION_UNAVAILABLE_ERROR);
    }
}
