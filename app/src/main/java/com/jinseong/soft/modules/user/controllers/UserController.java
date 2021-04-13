package com.jinseong.soft.modules.user.controllers;

import com.jinseong.soft.modules.user.application.UserService;
import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.dto.UserRegistrationData;
import com.jinseong.soft.modules.user.dto.UserResponseData;
import com.jinseong.soft.modules.user.dto.UserUpdateData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("{id}")
    UserResponseData updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateData updateData) {
        User user = userService.updateUser(id, updateData);
        return getUserResultData(user);
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
