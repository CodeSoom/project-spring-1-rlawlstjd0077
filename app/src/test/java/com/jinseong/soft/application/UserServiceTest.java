package com.jinseong.soft.application;

import com.jinseong.soft.UserTestFixture;
import com.jinseong.soft.domain.User;
import com.jinseong.soft.domain.UserRepository;
import com.jinseong.soft.dto.UserRegistrationData;
import com.jinseong.soft.errors.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class UserServiceTest {
    UserService userService;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);

        User user = UserTestFixture.generateUser();

        given(userRepository.save(any(User.class))).willReturn(user);

        given(userRepository.findByIdAndDeletedIsFalse(UserTestFixture.EXIST_USER_ID))
                .willReturn(Optional.of(user));
    }

    @DisplayName("createUser()")
    @Nested
    class Describe_createUser {
        UserRegistrationData givenUser;

        @BeforeEach
        void setUp() {
            User user = UserTestFixture.generateUser();
            givenUser = UserRegistrationData.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .name(user.getName())
                    .build();
        }

        @DisplayName("생성된 유저를 반환한다")
        @Test
        void it_returns_created_user() {
            User user = userService.registerUser(givenUser);

            assertThat(user.getEmail()).isEqualTo(givenUser.getEmail());
            assertThat(user.getName()).isEqualTo(givenUser.getName());
            assertThat(user.getPassword()).isEqualTo(givenUser.getPassword());
            assertThat(user.isDeleted()).isFalse();
        }
    }

    @DisplayName("updateUser()")
    @Nested
    class Describe_updateUser {
        @DisplayName("존재하는 user id가 주어진 경우")
        @Nested
        class Context_with_exist_user_id {
            Long givenUserId = UserTestFixture.EXIST_USER_ID;

            @DisplayName("수정된 유저를 반환한다")
            @Test
            void it_returns_deleted_user() {
                User source = UserTestFixture.UPDATE_USER;
                User user = userService.updateUser(givenUserId, source);

                assertThat(user.getName()).isEqualTo(source.getName());
                assertThat(user.getPassword()).isEqualTo(source.getPassword());
                assertThat(user.isDeleted()).isFalse();
            }
        }

        @DisplayName("존재하지 않는 user id가 주어진 경우")
        @Nested
        class Context_with_not_exist_user_id {
            Long givenUserId = UserTestFixture.NOT_EXIST_USER_ID;

            @DisplayName("유저를 찾을 수 없다는 예외를 반환한다")
            @Test
            void it_returns_user_found_exception() {
                User source = UserTestFixture.UPDATE_USER;
                assertThrows(UserNotFoundException.class, () -> userService.updateUser(givenUserId, source));
            }
        }
    }

    @DisplayName("deleteUser()")
    @Nested
    class Describe_deleteUser {
        @BeforeEach
        void setUp() {
            User deletedUser = UserTestFixture.generateUser();
            deletedUser.destroy();

            given(userRepository.findByIdAndDeletedIsFalse(eq(UserTestFixture.EXIST_USER_ID)))
                    .willReturn(Optional.of(deletedUser));
            given(userRepository.findByIdAndDeletedIsFalse(eq(UserTestFixture.NOT_EXIST_USER_ID)))
                    .willReturn(Optional.empty());
        }

        @DisplayName("존재하는 user id가 주어진 경우")
        @Nested
        class Context_with_exist_user_id {
            Long givenUserId = UserTestFixture.EXIST_USER_ID;


            @DisplayName("삭제된 유저를 반환한다")
            @Test
            void it_returns_deleted_user() {
                User user = userService.deleteUser(givenUserId);

                assertThat(user.getEmail()).isEqualTo(UserTestFixture.EXIST_USER.getEmail());
                assertThat(user.getName()).isEqualTo(UserTestFixture.EXIST_USER.getName());
                assertThat(user.getPassword()).isEqualTo(UserTestFixture.EXIST_USER.getPassword());
                assertThat(user.isDeleted()).isTrue();
            }
        }

        @DisplayName("존재하지 않는 user id가 주어진 경우")
        @Nested
        class Context_with_not_exist_user_id {
            Long givenUserId = UserTestFixture.NOT_EXIST_USER_ID;

            @DisplayName("유저를 찾을 수 없다는 예외를 반환한다")
            @Test
            void it_returns_user_found_exception() {
                assertThrows(UserNotFoundException.class, () -> userService.deleteUser(givenUserId));
            }
        }
    }
}
