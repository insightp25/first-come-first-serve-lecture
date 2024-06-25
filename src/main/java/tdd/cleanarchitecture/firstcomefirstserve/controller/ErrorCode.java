package tdd.cleanarchitecture.firstcomefirstserve.controller;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SESSION_UNAVAILABLE_ERROR("409", "본 강의는 신청이 마감되었습니다"),
    GENERAL_ERROR("500", "서버 오류가 발생했습니다");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
