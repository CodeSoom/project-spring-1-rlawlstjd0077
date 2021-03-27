package com.jinseong.soft.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinseong.soft.LinkTestFixture;
import com.jinseong.soft.application.LinkService;
import com.jinseong.soft.domain.Link;
import com.jinseong.soft.errors.LinkNotFoundException;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkController.class)
class LinkControllerTest {
    private static final Link LINK = LinkTestFixture.LINK;

    @MockBean
    LinkService linkService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(linkService);
        given(linkService.createLink(any(Link.class))).willReturn(LINK);
        given(linkService.getLink(LinkTestFixture.EXIST_ID)).willReturn(LINK);
        given(linkService.getLink(LinkTestFixture.NOT_EXIST_ID))
                .willThrow(LinkNotFoundException.class);
        given(linkService.updateLink(eq(LinkTestFixture.EXIST_ID), any(Link.class)))
                .willReturn(LinkTestFixture.UPDATE_LINK);
        given(linkService.updateLink(eq(LinkTestFixture.NOT_EXIST_ID), any(Link.class)))
                .willThrow(LinkNotFoundException.class);
    }

    @Nested
    @DisplayName("GET /links/{id} 요청은")
    class Describe_GET_link_by_id {
        @Nested
        @DisplayName("존재하는 링크 id가 주어진다면")
        class Context_with_exist_link_id {
            long givenLinkId = LinkTestFixture.EXIST_ID;

            @DisplayName("OK 코드와 주어진 id와 일치하는 링크를 응답한다")
            @Test
            void It_returns_ok_status_with_link() throws Exception {
                mockMvc.perform(
                        get("/links/{id}", givenLinkId)
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(LINK.getTitle())));

                verify(linkService).getLink(givenLinkId);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 링크 id가 주어진다면")
        class Context_with_not_exist_link_id {
            long givenLinkId = LinkTestFixture.NOT_EXIST_ID;

            @DisplayName("NOT FOUND 코드를 응답한다")
            @Test
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

            @Test
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

            @Test
            @DisplayName("OK 상태코드와 링크 목록을 응답한다")
            void It_returns_status_ok_and_empty_list() throws Exception {
                mockMvc.perform(
                        get("/links")
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(LINK.getTitle())));

                verify(linkService).getLinks();
            }
        }
    }

    @Nested
    @DisplayName("POST /links 요청은")
    class Describe_POST {
        @Test
        @DisplayName("CREATED 상태 코드와 생성된 링크를 응답한다")
        void It_returns_created_status_with_created_link() throws Exception {
            mockMvc.perform(
                    post("/links")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(LINK))
            )
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString(LINK.getTitle())));
        }
    }

    @Nested
    @DisplayName("PATCH /links 요청은")
    class Describe_PATCH {
        @Nested
        @DisplayName("존재하는 링크 id가 주어진다면")
        class Context_with_exist_link_id {
            long givenLinkId = LinkTestFixture.EXIST_ID;
            Link updateSource = LinkTestFixture.UPDATE_LINK;

            @Test
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
        @DisplayName("존재하지 않는 링크 id가 주어진다면")
        class Context_with_not_exist_link_id {
            long givenLinkId = LinkTestFixture.NOT_EXIST_ID;
            Link updateSource = LinkTestFixture.UPDATE_LINK;

            @Test
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
}
