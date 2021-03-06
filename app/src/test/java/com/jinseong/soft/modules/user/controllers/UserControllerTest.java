package com.jinseong.soft.modules.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinseong.soft.ControllerTest;
import com.jinseong.soft.fixtures.UserTestFixture;
import com.jinseong.soft.modules.user.application.UserService;
import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.domain.UserNotFoundException;
import com.jinseong.soft.modules.user.dto.UserRegistrationData;
import com.jinseong.soft.modules.user.dto.UserUpdateData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final UserRegistrationData USER = UserRegistrationData.builder()
            .email(UserTestFixture.EXIST_USER.getEmail())
            .password(UserTestFixture.EXIST_USER.getPassword())
            .name(UserTestFixture.EXIST_USER.getName())
            .build();

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(userService);
        given(userService.registerUser(any(UserRegistrationData.class)))
                .willReturn(UserTestFixture.EXIST_USER);
        given(userService.destroyUser(UserTestFixture.NOT_EXIST_USER_ID))
                .willThrow(UserNotFoundException.class);
        given(userService.updateUser(eq(UserTestFixture.EXIST_USER_ID), any(UserUpdateData.class)))
                .will(invocation -> User.builder()
                        .id(invocation.getArgument(0))
                        .name(((UserUpdateData) invocation.getArgument(1)).getName())
                        .email(UserTestFixture.EXIST_USER.getEmail())
                        .build());
        given(userService.updateUser(eq(UserTestFixture.NOT_EXIST_USER_ID), any(UserUpdateData.class)))
                .willThrow(UserNotFoundException.class);
    }


    @DisplayName("POST /users ?????????")
    @Nested
    class Describe_POST_users {
        @ControllerTest
        @DisplayName("CREATED ?????? ????????? ????????? ????????? ????????????")
        void It_returns_created_status_with_created_user() throws Exception {
            mockMvc.perform(
                    post("/users")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(USER))
            )
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString(USER.getName())))
                    .andExpect(content().string(containsString(USER.getEmail())));
        }
    }

    @DisplayName("PATCH /users/{id} ?????????")
    @Nested
    class Describe_PATCH_users {
        UserUpdateData updateSource = UserUpdateData.builder()
                .name(UserTestFixture.UPDATE_USER.getName())
                .password(UserTestFixture.UPDATE_USER.getPassword())
                .build();

        @DisplayName("???????????? user id??? ?????? ?????? ????????? ????????? ??????")
        @Nested
        class Context_with_exist_user_id {
            Long givenUserId = UserTestFixture.EXIST_USER_ID;


            @ControllerTest
            @DisplayName("OK ????????? ????????? ????????? ????????????")
            void It_returns_ok_status_with_updated_link() throws Exception {
                mockMvc.perform(
                        patch("/users/{id}", givenUserId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource))
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(UserTestFixture.EXIST_USER.getEmail())))
                        .andExpect(content().string(containsString(updateSource.getName())));
            }
        }

        @DisplayName("???????????? ?????? user id??? ????????? ??????")
        @Nested
        class Context_with_not_exist_user_id {
            Long givenUserId = UserTestFixture.NOT_EXIST_USER_ID;

            @ControllerTest
            @DisplayName("NOT FOUND ????????? ????????????")
            void It_returns_not_found_status() throws Exception {
                mockMvc.perform(
                        patch("/users/{id}", givenUserId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource))
                )
                        .andExpect(status().isNotFound());
            }
        }

        @DisplayName("DELETE /users/{id} ?????????")
        @Nested
        class Describe_DELETE_users {
            @DisplayName("???????????? user id??? ????????? ??????")
            @Nested
            class Context_with_exist_user_id {
                Long givenUserId = UserTestFixture.EXIST_USER_ID;

                @ControllerTest
                @DisplayName("NO CONTENT ????????? ????????????")
                void It_returns_ok_status_with_updated_link() throws Exception {
                    mockMvc.perform(
                            delete("/users/{id}", givenUserId)
                    )
                            .andExpect(status().isNoContent());
                }
            }

            @DisplayName("???????????? ?????? user id??? ????????? ??????")
            @Nested
            class Context_with_not_exist_user_id {
                Long givenUserId = UserTestFixture.NOT_EXIST_USER_ID;

                @ControllerTest
                @DisplayName("NOT FOUND ????????? ????????????")
                void It_returns_not_found_status() throws Exception {
                    mockMvc.perform(
                            delete("/users/{id}", givenUserId)
                    )
                            .andExpect(status().isNotFound());

                }
            }
        }
    }
}
