package tdd.cleanarchitecture.firstcomefirstserve.common.domain.exception;

import tdd.cleanarchitecture.firstcomefirstserve.common.controller.exception.BaseException;
import tdd.cleanarchitecture.firstcomefirstserve.common.controller.ErrorCode;

public class SessionUnavailableException extends BaseException {
    public SessionUnavailableException() {
        super(ErrorCode.SESSION_UNAVAILABLE_ERROR);
    }
}
