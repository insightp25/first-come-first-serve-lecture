package tdd.cleanarchitecture.firstcomefirstserve.common.domain.exception;

import tdd.cleanarchitecture.firstcomefirstserve.common.controller.ErrorCode;
import tdd.cleanarchitecture.firstcomefirstserve.common.controller.exception.BaseException;

public class SessionNotFoundException extends BaseException {
    public SessionNotFoundException() {
        super(ErrorCode.SESSION_NOT_FOUND_ERROR);
    }
}
