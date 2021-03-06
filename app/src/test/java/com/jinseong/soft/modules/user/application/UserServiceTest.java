package com.jinseong.soft.modules.user.application;

import com.jinseong.soft.fixtures.UserTestFixture;
import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.domain.UserEmailDuplicationException;
import com.jinseong.soft.modules.user.domain.UserNotFoundException;
import com.jinseong.soft.modules.user.domain.UserRepository;
import com.jinseong.soft.modules.user.dto.UserRegistrationData;
import com.jinseong.soft.modules.user.dto.UserUpdateData;
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

        @DisplayName("????????? ?????? ????????? ????????? ??????")
        @Nested
        class Context_with_valid_user {
            @DisplayName("????????? ????????? ????????????")
            @Test
            void it_returns_created_user() {
                User user = userService.registerUser(givenUser);

                assertThat(user.getEmail()).isEqualTo(givenUser.getEmail());
                assertThat(user.getName()).isEqualTo(givenUser.getName());
                assertThat(user.getPassword()).isEqualTo(givenUser.getPassword());
                assertThat(user.isDeleted()).isFalse();
            }
        }

        @DisplayName("????????? email ????????? ?????? ?????? ????????? ????????? ??????")
        @Nested
        class Context_with_duplication_email_user {
            @BeforeEach
            void setUp() {
                given(userRepository.existsByEmail(eq(givenUser.getEmail())))
                        .willReturn(true);
            }

            @DisplayName("???????????? ?????????????????? ????????? ?????????")
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

        @DisplayName("???????????? user id??? ?????? ?????? ????????? ????????? ??????")
        @Nested
        class Context_with_exist_user_id {
            Long givenUserId = UserTestFixture.EXIST_USER_ID;

            @DisplayName("????????? ????????? ????????????")
            @Test
            void it_returns_deleted_user() {
                User user = userService.updateUser(givenUserId, source);

                assertThat(user.getName()).isEqualTo(source.getName());
                assertThat(user.getPassword()).isEqualTo(source.getPassword());
                assertThat(user.isDeleted()).isFalse();
            }
        }

        @DisplayName("???????????? ?????? user id??? ????????? ??????")
        @Nested
        class Context_with_not_exist_user_id {
            Long givenUserId = UserTestFixture.NOT_EXIST_USER_ID;

            @DisplayName("????????? ?????? ??? ????????? ????????? ????????????")
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

        @DisplayName("???????????? user id??? ????????? ??????")
        @Nested
        class Context_with_exist_user_id {
            Long givenUserId = UserTestFixture.EXIST_USER_ID;


            @DisplayName("????????? ????????? ????????????")
            @Test
            void it_returns_deleted_user() {
                User user = userService.destroyUser(givenUserId);

                assertThat(user.getEmail()).isEqualTo(UserTestFixture.EXIST_USER.getEmail());
                assertThat(user.getName()).isEqualTo(UserTestFixture.EXIST_USER.getName());
                assertThat(user.getPassword()).isEqualTo(UserTestFixture.EXIST_USER.getPassword());
                assertThat(user.isDeleted()).isTrue();
            }
        }

        @DisplayName("???????????? ?????? user id??? ????????? ??????")
        @Nested
        class Context_with_not_exist_user_id {
            Long givenUserId = UserTestFixture.NOT_EXIST_USER_ID;

            @DisplayName("????????? ?????? ??? ????????? ????????? ????????????")
            @Test
            void it_returns_user_found_exception() {
                assertThrows(UserNotFoundException.class, () -> userService.destroyUser(givenUserId));
            }
        }
    }
}
