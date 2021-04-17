package com.jinseong.soft.modules.link.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinseong.soft.ControllerTest;
import com.jinseong.soft.fixtures.LinkTestFixture;
import com.jinseong.soft.fixtures.UserTestFixture;
import com.jinseong.soft.modules.category.application.CategoryService;
import com.jinseong.soft.modules.link.application.LinkService;
import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.link.domain.LinkNotFoundException;
import com.jinseong.soft.modules.link.dto.LinkRequestData;
import com.jinseong.soft.modules.tag.application.TagService;
import com.jinseong.soft.modules.type.application.TypeService;
import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkController.class)
class LinkControllerTest {
    private static final LinkRequestData LINK_REQUEST = LinkTestFixture.LINK_REQUEST;
    private static final Link LINK = LinkTestFixture.generateLink();
    private static final Link UPDATE_LINK = LinkTestFixture.generateUpdateLink();

    @MockBean
    TagService tagService;

    @MockBean
    TypeService typeService;

    @MockBean
    CategoryService categoryService;

    @MockBean
    LinkService linkService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    @WithMockUser
    void setUp() {
        Mockito.reset(linkService);
        given(linkService.createLink(any(LinkRequestData.class), any(User.class))).willReturn(LINK);
        given(linkService.getLink(LinkTestFixture.EXIST_ID)).willReturn(LINK);
        given(linkService.getLink(LinkTestFixture.NOT_EXIST_ID))
                .willThrow(LinkNotFoundException.class);

        given(linkService.updateLink(eq(LinkTestFixture.EXIST_ID), any(LinkRequestData.class)))
                .willReturn(UPDATE_LINK);
        given(linkService.updateLink(eq(LinkTestFixture.NOT_EXIST_ID), any(LinkRequestData.class)))
                .willThrow(LinkNotFoundException.class);

        given(linkService.deleteLink(eq(LinkTestFixture.EXIST_ID)))
                .willReturn(LINK);
        given(linkService.deleteLink(eq(LinkTestFixture.NOT_EXIST_ID)))
                .willThrow(LinkNotFoundException.class);

        given(userRepository.findByEmail("user"))
                .willReturn(Optional.of(UserTestFixture.EXIST_USER));
    }

    @Nested
    @DisplayName("GET /links/{id} 요청은")
    class Describe_GET_link_by_id {
        @Nested
        @DisplayName("존재하는 링크 id가 주어진다면")
        class Context_with_exist_link_id {
            long givenLinkId = LinkTestFixture.EXIST_ID;

            @DisplayName("OK 코드와 주어진 id와 일치하는 링크를 응답한다")
            @ControllerTest
            void It_returns_ok_status_with_link() throws Exception {
                mockMvc.perform(
                        get("/links/{id}", givenLinkId)
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(LINK_REQUEST.getTitle())));

                verify(linkService).getLink(givenLinkId);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 링크 id가 주어진다면")
        class Context_with_not_exist_link_id {
            long givenLinkId = LinkTestFixture.NOT_EXIST_ID;

            @DisplayName("NOT FOUND 코드를 응답한다")
            @ControllerTest
            void It_returns_not_found_code() throws Exception {
                mockMvc.perform(
                        get("/links/{id}", givenLinkId)
                )
                        .andExpect(status().isNotFound());

                verify(linkService).getLink(givenLinkId);

            }
        }
    }

    @Nested
    @DisplayName("GET /links 요청은")
    class Describe_GET_links {

        @Nested
        @DisplayName("링크가 존재하지 않는 경우")
        class Context_with_no_link {

            @ControllerTest
            @DisplayName("OK 상태코드와 빈 링크 목록을 응답한다")
            void It_returns_status_ok_and_empty_list() throws Exception {
                mockMvc.perform(
                        get("/links")
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));

                verify(linkService).getLinks();
            }
        }

        @Nested
        @DisplayName("링크가 존재하는 경우")
        class Context_with_links {
            @BeforeEach
            void setUp() {
                given(linkService.getLinks())
                        .willReturn(Collections.singletonList(LINK));
            }

            @ControllerTest
            @DisplayName("OK 상태코드와 링크 목록을 응답한다")
            void It_returns_status_ok_and_empty_list() throws Exception {
                mockMvc.perform(
                        get("/links")
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(LINK_REQUEST.getTitle())));

                verify(linkService).getLinks();
            }
        }
    }

    @Nested
    @DisplayName("POST /links 요청은")
    class Describe_POST {
        @ControllerTest
        @DisplayName("CREATED 상태 코드와 생성된 링크를 응답한다")
        void It_returns_created_status_with_created_link() throws Exception {
            mockMvc.perform(
                    post("/links")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(LINK_REQUEST))
            )
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString(LINK_REQUEST.getTitle())));
        }
    }

    @Nested
    @DisplayName("PATCH /links 요청은")
    class Describe_PATCH {
        @Nested
        @DisplayName("존재하는 링크 id와 링크 수정 정보가 주어진다면")
        class Context_with_exist_link_id {
            long givenLinkId = LinkTestFixture.EXIST_ID;
            LinkRequestData updateSource = LinkTestFixture.UPDATE_LINK_REQUEST;

            @ControllerTest
            @DisplayName("OK 코드와 수정된 링크를 응답한다")
            void It_returns_ok_status_with_updated_link() throws Exception {
                mockMvc.perform(
                        patch("/links/{id}", givenLinkId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource))
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(updateSource.getTitle())));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 링크 id와 링크 수정 정보가 주어진다면")
        class Context_with_not_exist_link_id {
            long givenLinkId = LinkTestFixture.NOT_EXIST_ID;
            LinkRequestData updateSource = LinkTestFixture.UPDATE_LINK_REQUEST;

            @ControllerTest
            @DisplayName("NOT FOUND 코드를 응답한다")
            void It_returns_not_found_status() throws Exception {
                mockMvc.perform(
                        patch("/links/{id}", givenLinkId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateSource))
                )
                        .andExpect(status().isNotFound());

            }
        }
    }

    @Nested
    @DisplayName("DELETE /links/{id}")
    class Describe_DELETE {
        @Nested
        @DisplayName("존재하는 링크 id가 주어진다면")
        class Context_with_exist_link_id {
            long givenLinkId = LinkTestFixture.EXIST_ID;

            @ControllerTest
            @DisplayName("NO CONTENT 코드를 응답한다")
            void It_returns_no_content() throws Exception {
                mockMvc.perform(
                        delete("/links/{id}", givenLinkId)
                )
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 링크 id가 주어진다면")
        class Context_with_not_exist_link_id {
            long givenLinkId = LinkTestFixture.NOT_EXIST_ID;

            @ControllerTest
            @DisplayName("NOT FOUND 코드를 응답한다")
            void It_returns_not_found_status() throws Exception {
                mockMvc.perform(
                        delete("/links/{id}", givenLinkId)
                )
                        .andExpect(status().isNotFound());

            }
        }
    }

    @Nested
    @DisplayName("POST /links/like/{id}")
    class Describe_POST_LINK_LIKE {
        @Nested
        @DisplayName("존재하는 링크 id에 대해서 처음으로 좋아요를 추가한 유저인경우")
        class Context_with_exist_link_id_and_first_like_user {
            long givenLinkId = LinkTestFixture.EXIST_ID;

            @BeforeEach
            void setUp() {
                given(linkService.addLike(eq(LinkTestFixture.EXIST_ID), any(User.class)))
                        .willReturn(true);
            }

            @ControllerTest
            @DisplayName("OK 코드와 true 값을 응답한다")
            void It_returns_ok_and_true() throws Exception {
                mockMvc.perform(
                        post("/links/like/{id}", givenLinkId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(LINK_REQUEST))
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string("true"));
            }
        }

        @Nested
        @DisplayName("존재하는 링크 id에 대해서 이미 좋아요를 추가한 유저인경우")
        class Context_with_exist_link_id_and_already_like_user {
            long givenLinkId = LinkTestFixture.EXIST_ID;

            @BeforeEach
            void setUp() {
                given(linkService.addLike(eq(LinkTestFixture.EXIST_ID), any(User.class)))
                        .willReturn(false);
            }

            @ControllerTest
            @DisplayName("OK 코드와 false 값을 응답한다")
            void It_returns_ok_and_false() throws Exception {
                mockMvc.perform(
                        post("/links/like/{id}", givenLinkId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(LINK_REQUEST))
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string("false"));
            }
        }
    }
}
