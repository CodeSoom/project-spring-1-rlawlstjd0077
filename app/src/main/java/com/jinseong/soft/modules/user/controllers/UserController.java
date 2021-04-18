package com.jinseong.soft.modules.user.controllers;

import com.jinseong.soft.modules.user.application.UserService;
import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.dto.UserRegistrationData;
import com.jinseong.soft.modules.user.dto.UserResponseData;
import com.jinseong.soft.modules.user.dto.UserUpdateData;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    /**
     * 주어진 유저 생성 정보로 유저를 생성한 뒤 응답합니다.
     *
     * @param registrationData 유저 생성 정보
     * @return 생성된 유저 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponseData createUser(@RequestBody UserRegistrationData registrationData) {
        User user = userService.registerUser(registrationData);
        return getUserResultData(user);
    }

    /**
     * 대응되는 식별자의 유저를 삭제합니다.
     *
     * @param id 유저 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyUser(@PathVariable Long id) {
        userService.destroyUser(id);
    }

    /**
     * 대응되는 식별자의 유저를 주어진 유저 정보로 수정한 뒤 응답합니다.
     *
     * @param id         유저 식별자
     * @param updateData 유저 수정 정보
     * @return 수정된 유저
     */
    @PatchMapping("{id}")
    UserResponseData updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateData updateData) {
        User user = userService.updateUser(id, updateData);
        return getUserResultData(user);
    }


    /**
     * 존재하는 모든 유저 이름 목록을 응답합니다.
     *
     * @return 유저 이름 목록
     */
    @GetMapping
    public List<String> getUsers() {
        return userService.getUserNames();
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
