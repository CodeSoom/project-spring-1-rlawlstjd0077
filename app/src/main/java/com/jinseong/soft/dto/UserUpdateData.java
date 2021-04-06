package com.jinseong.soft.dto;

import com.jinseong.soft.domain.UserNameGettable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 수정 요청 정보.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateData implements UserNameGettable {
    private String name;

    private String password;
}
