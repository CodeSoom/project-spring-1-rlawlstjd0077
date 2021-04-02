package com.jinseong.soft;

import com.jinseong.soft.domain.User;

public class UserTestFixture {
    public static final Long EXIST_USER_ID = 0L;
    public static final Long NOT_EXIST_USER_ID = -10000L;

    public static final User EXIST_USER = generateUser();

    public static User generateUser() {
        return User.builder()
                .id(EXIST_USER_ID)
                .email("test@email.com")
                .name("test")
                .password("1234")
                .build();
    }
}
