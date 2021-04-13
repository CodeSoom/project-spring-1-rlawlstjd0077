package com.jinseong.soft.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 응답 정보.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseData {
    private Long id;

    private String email;

    private String name;
}
