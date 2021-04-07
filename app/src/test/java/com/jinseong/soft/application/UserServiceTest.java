package com.jinseong.soft.application;

import com.jinseong.soft.UserTestFixture;
import com.jinseong.soft.domain.User;
import com.jinseong.soft.domain.UserRepository;
import com.jinseong.soft.dto.UserRegistrationData;
import com.jinseong.soft.dto.UserUpdateData;
import com.jinseong.soft.errors.UserEmailDuplicationException;
import com.jinseong.soft.errors.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class UserServiceTest {
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, passwordEncoder);

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

        @DisplayName("유효한 유저 정보가 주어진 경우")
        @Nested
        class Context_with_valid_user {
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

        @DisplayName("중복된 email 정보를 가진 유저 정보가 주어진 경우")
        @Nested
        class Context_with_duplication_email_user {
            @BeforeEach
            void setUp() {
                given(userRepository.existsByEmail(eq(givenUser.getEmail())))
                        .willReturn(true);
            }

            @DisplayName("이메일이 중복되었다는 예외를 던진다")
            @Test
            void it_throws_duplication_email_exception() {
                assertThrows(UserEmailDuplicationException.class, () -> userService.registerUser(givenUser));
            }
        }
    }

    @DisplayName("updateUser()")
    @Nested
    class Describe_updateUser {
        UserUpdateData source = UserUpdateData.builder()
                .password(UserTestFixture.UPDATE_USER.getPassword())
                .name(UserTestFixture.UPDATE_USER.getName())
                .build();

        @DisplayName("존재하는 user id와 유저 수정 정보가 주어진 경우")
        @Nested
        class Context_with_exist_user_id {
            Long givenUserId = UserTestFixture.EXIST_USER_ID;

            @DisplayName("수정된 유저를 반환한다")
            @Test
            void it_returns_deleted_user() {
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
                User user = userService.destroyUser(givenUserId);

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
                assertThrows(UserNotFoundException.class, () -> userService.destroyUser(givenUserId));
            }
        }
    }
}
