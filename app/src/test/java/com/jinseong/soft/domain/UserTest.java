package com.jinseong.soft.domain;

import com.jinseong.soft.UserTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void changeWith() {
        User user = new User();
        User source = User.builder()
                .name("test")
                .password("1234")
                .build();

        user.changeWith(source);

        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.getPassword()).isEqualTo("");
        assertThat(user.isDeleted()).isFalse();
    }

    @Test
    void destroyUser() {
        User user = new User();
        user.destroy();
        assertThat(user.isDeleted()).isTrue();
    }

    @Test
    void changeUserPassword() {
        String password = "1234";
        User user = new User();
        User source = User.builder()
                .name("test")
                .password("1234")
                .build();

        user.changeWith(source);
        user.changePassword(password, passwordEncoder);

        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.authenticated(password, passwordEncoder)).isTrue();
    }
}
