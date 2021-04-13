package com.jinseong.soft.fixtures;

import com.jinseong.soft.modules.user.domain.User;

public class UserTestFixture {
    public static final Long EXIST_USER_ID = 0L;
    public static final Long NOT_EXIST_USER_ID = -10000L;

    public static final User EXIST_USER = generateUser();
    public static final User UPDATE_USER = User.builder()
            .id(EXIST_USER_ID)
            .email("update@email.com")
            .name("update")
            .password("1234")
            .build();


    public static User generateUser() {
        return User.builder()
                .id(EXIST_USER_ID)
                .email("test@email.com")
                .name("test")
                .password("1234")
                .build();
    }
}
