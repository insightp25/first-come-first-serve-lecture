package tdd.cleanarchitecture.firstcomefirstserve.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tdd.cleanarchitecture.firstcomefirstserve.controller.exception.BaseException;
import tdd.cleanarchitecture.firstcomefirstserve.controller.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.trace("Customized exception: ", e);

        return ResponseEntity
            .status(Integer.parseInt(errorCode.getCode()))
            .body(new ErrorResponse(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode generalError = ErrorCode.GENERAL_ERROR;
        log.error("General error: ", e);

        return ResponseEntity
            .status(Integer.parseInt(generalError.getCode()))
            .body(new ErrorResponse(generalError.getCode(), generalError.getMessage()));
    }
}
