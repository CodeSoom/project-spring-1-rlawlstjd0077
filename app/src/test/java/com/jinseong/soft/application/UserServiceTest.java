package com.jinseong.soft.application;

import com.jinseong.soft.UserTestFixture;
import com.jinseong.soft.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    UserService userService = new UserService();

    @DisplayName("createUser()")
    @Nested
    class Describe_createUser {
        User givenUser = UserTestFixture.generateUser();

        @DisplayName("생성된 유저를 반환한다")
        @Test
        void it_returns_created_user() {
            User user = userService.createUser(givenUser);

            assertThat(user.getEmail()).isEqualTo(givenUser.getEmail());
            assertThat(user.getName()).isEqualTo(givenUser.getName());
            assertThat(user.getPassword()).isEqualTo(givenUser.getPassword());
            assertThat(user.isDeleted()).isFalse();
        }
    }
}
