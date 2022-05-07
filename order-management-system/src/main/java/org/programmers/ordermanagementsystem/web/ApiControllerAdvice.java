package org.programmers.ordermanagementsystem.web;

import lombok.extern.slf4j.Slf4j;
import org.programmers.ordermanagementsystem.exception.NotEnoughStockException;
import org.programmers.ordermanagementsystem.exception.ProhibitedItemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorData handleProhibitedItemException(ProhibitedItemException e) {
        return new ApiErrorData("청소년보호법에 의한 구입 불가 품목", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiErrorData handleNotEnoughStockException(NotEnoughStockException e) {
        return new ApiErrorData("재고 부족", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiErrorData handleOtherException(Exception e) {
        log.error("API: 식별되지 않은 오류에 대해 사용자에게 메시지 전달함, 에러 정보:", e);
        return new ApiErrorData("서버 내부 오류", "죄송합니다.");
    }

    record ApiErrorData(String exceptionName, String message) {
    }
}
