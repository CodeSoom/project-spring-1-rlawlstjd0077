package com.jinseong.soft.controllers;

import com.jinseong.soft.application.UserService;
import com.jinseong.soft.domain.User;
import com.jinseong.soft.dto.UserRegistrationData;
import com.jinseong.soft.dto.UserResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 HTTP 요청 핸들러
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponseData createUser(@RequestBody UserRegistrationData registrationData) {
        User user = userService.registerUser(registrationData);
        return getUserResultData(user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyUser(@PathVariable Long id) {
        userService.destroyUser(id);
    }

    /**
     * 주어진 유저를 유저 응답 정보로 반환합니다.
     *
     * @param user 유저
     * @return 유저 응답 정보
     */
    private UserResponseData getUserResultData(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
