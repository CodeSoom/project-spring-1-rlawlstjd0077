package com.jinseong.soft.controllers;

import com.jinseong.soft.dto.ErrorResponse;
import com.jinseong.soft.errors.LinkNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 전역 HTTP 에러 응답 핸들러.
 */
@ResponseBody
@ControllerAdvice
public class ControllerErrorAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LinkNotFoundException.class)
    public ErrorResponse handleProductNotFound() {
        return new ErrorResponse("Link not found");
    }
}
