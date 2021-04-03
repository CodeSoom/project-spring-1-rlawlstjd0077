package com.jinseong.soft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 생성 요청 정보.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationData {
    private String email;

    private String name;

    private String password;
}
