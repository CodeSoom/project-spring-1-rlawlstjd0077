package com.jinseong.soft.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinseong.soft.UserTestFixture;
import com.jinseong.soft.application.UserService;
import com.jinseong.soft.dto.UserRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        given(userService.registerUser(any(UserRegistrationData.class))).willReturn(UserTestFixture.EXIST_USER);
    }


    @DisplayName("POST /users 요청은")
    @Nested
    class Describe_POST_users {
        @Test
        @DisplayName("CREATED 상태 코드와 생성된 유저를 응답한다")
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
}
