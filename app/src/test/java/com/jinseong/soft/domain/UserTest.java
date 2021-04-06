package com.jinseong.soft.domain;

import com.jinseong.soft.UserTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = UserTestFixture.generateUser();
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

    @Test
    void changeUserPassword() {
        String password = "12345";

        user.changePassword(password);
    }
}
