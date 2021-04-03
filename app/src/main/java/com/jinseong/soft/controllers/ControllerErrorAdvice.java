package com.jinseong.soft.controllers;

import com.jinseong.soft.dto.ErrorResponse;
import com.jinseong.soft.errors.LinkNotFoundException;
import com.jinseong.soft.errors.UserNotFoundException;
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
    public ErrorResponse handleLinkNotFound() {
        return new ErrorResponse("Link not found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound() {
        return new ErrorResponse("User not found");
    }
}
