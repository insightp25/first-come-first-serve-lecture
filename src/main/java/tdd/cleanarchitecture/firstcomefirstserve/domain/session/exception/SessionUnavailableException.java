package tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception;

import tdd.cleanarchitecture.firstcomefirstserve.controller.common.exception.BaseException;
import tdd.cleanarchitecture.firstcomefirstserve.controller.common.ErrorCode;

public class SessionUnavailableException extends BaseException {
    public SessionUnavailableException() {
        super(ErrorCode.SESSION_UNAVAILABLE_ERROR);
    }
}
