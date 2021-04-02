package com.jinseong.soft;

import com.jinseong.soft.domain.User;

public class UserTestFixture {
    public static User generateUser() {
        return User.builder()
                .id(0L)
                .email("test@email.com")
                .name("test")
                .password("1234")
                .build();
    }
}
