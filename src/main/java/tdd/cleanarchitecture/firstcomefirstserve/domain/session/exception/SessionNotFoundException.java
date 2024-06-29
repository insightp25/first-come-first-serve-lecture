package tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception;

import tdd.cleanarchitecture.firstcomefirstserve.controller.common.ErrorCode;
import tdd.cleanarchitecture.firstcomefirstserve.controller.common.exception.BaseException;

public class SessionNotFoundException extends BaseException {
    public SessionNotFoundException() {
        super(ErrorCode.SESSION_NOT_FOUND_ERROR);
    }
}
