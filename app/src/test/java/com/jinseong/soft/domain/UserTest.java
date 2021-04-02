package com.jinseong.soft.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(0L)
                .email("test@email.com")
                .name("test")
                .password("1234")
                .build();
    }

    @Test
    void createUserWithBuilder() {
        assertThat(user.getEmail()).isEqualTo("test@email.com");
        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.isDeleted()).isFalse();
    }

    @Test
    void destroyUser() {
        user.destroy();
        assertThat(user.isDeleted()).isTrue();
    }
}
