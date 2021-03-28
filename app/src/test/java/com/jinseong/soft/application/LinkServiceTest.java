package com.jinseong.soft.application;

import com.jinseong.soft.LinkTestFixture;
import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.LinkRepository;
import com.jinseong.soft.errors.LinkNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class LinkServiceTest {

    private LinkService linkService;
    private static final Link LINK = LinkTestFixture.LINK;

    private static final Long NOT_EXIST_ID = 1000L;

    @BeforeEach
    void setUp() {
        LinkRepository linkRepository = Mockito.mock(LinkRepository.class);
        linkService = new LinkService(linkRepository);

        given(linkRepository.findById(LINK.getId()))
                .willReturn(Optional.of(LINK));
        given(linkRepository.save(LINK))
                .willReturn(LINK);

    }

    @Nested
    @DisplayName("getLinks()")
    class Describe_getLinks {

        @Nested
        @DisplayName("링크가 존재하지 않을 때")
        class Context_with_no_link {

            @BeforeEach
            void setUp() {
                given(linkService.getLinks()).willReturn(Collections.emptyList());
            }

            @Test
            @DisplayName("비어있는 목록을 반환한다")
            void It_returns_empty_list() {
                List<Link> linkList = linkService.getLinks();

                assertThat(linkList).isEmpty();
            }
        }

        @Nested
        @DisplayName("링크가 존재하는 경우")
        class Context_with_links {
            @BeforeEach
            void setUp() {
                given(linkService.getLinks()).willReturn(Collections.singletonList(LINK));
            }

            @Test
            @DisplayName("존재하는 링크 목록을 반환한다")
            void It_returns_exist_link_list() {
                List<Link> linkList = linkService.getLinks();

                assertThat(linkList).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getLink()")
    class Describe_getLink {
        @Nested
        @DisplayName("존재하는 링크 id가 주어진다면")
        class Context_exist_link_id {
            Long givenLinkId = LINK.getId();

            @BeforeEach
            void setUp() {
                linkService.createLink(LINK);
            }

            @Test
            @DisplayName("주어진 id와 일치하는 링크를 수정한 뒤 반환한다.")
            void it_returns_updated_link() {
                Link link = linkService.getLink(givenLinkId);

                assertThat(link.getTitle()).isEqualTo(LINK.getTitle());
                assertThat(link.getLinkURL()).isEqualTo(LINK.getLinkURL());
                assertThat(link.getCategory()).isEqualTo(LINK.getCategory());
                assertThat(link.getType()).isEqualTo(LINK.getType());
                assertThat(link.getDescription()).isEqualTo(LINK.getDescription());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 링크 id가 주어진다면")
        class Context_not_exist_link_id {
            Long givenLinkID = NOT_EXIST_ID;

            @Test
            @DisplayName("링크를 찾을 수 없다는 예외를 던진다")
            void it_returns_link_not_found_exception() {
                assertThrows(LinkNotFoundException.class, () -> linkService.getLink(givenLinkID));
            }
        }
    }

    @Nested
    @DisplayName("createLink()")
    class Describe_createLink {
        Link givenLink = LINK;

        @Test
        @DisplayName("주어진 링크를 저장한 뒤 반환한다.")
        void it_returns_saved_link() {
            Link link = linkService.createLink(givenLink);

            assertThat(link.getTitle()).isEqualTo(givenLink.getTitle());
            assertThat(link.getLinkURL()).isEqualTo(givenLink.getLinkURL());
            assertThat(link.getCategory()).isEqualTo(givenLink.getCategory());
            assertThat(link.getType()).isEqualTo(givenLink.getType());
            assertThat(link.getDescription()).isEqualTo(givenLink.getDescription());
        }
    }

    @Nested
    @DisplayName("updateLink()")
    class Describe_updateKLink {
        Link updateSource = LinkTestFixture.UPDATE_LINK;

        @Nested
        @DisplayName("존재하는 링크 id가 주어진다면")
        class Context_exist_link_id {
            Long givenLinkId = LINK.getId();

            @BeforeEach
            void setUp() {
                linkService.createLink(LINK);
            }

            @Test
            @DisplayName("주어진 id와 일치하는 링크를 수정한 뒤 반환한다.")
            void it_returns_updated_link() {
                Link link = linkService.updateLink(givenLinkId, updateSource);

                assertThat(link.getTitle()).isEqualTo(updateSource.getTitle());
                assertThat(link.getLinkURL()).isEqualTo(updateSource.getLinkURL());
                assertThat(link.getCategory()).isEqualTo(updateSource.getCategory());
                assertThat(link.getType()).isEqualTo(updateSource.getType());
                assertThat(link.getDescription()).isEqualTo(updateSource.getDescription());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 링크 id가 주어진다면")
        class Context_not_exist_link_id {
            Long givenLinkID = NOT_EXIST_ID;

            @Test
            @DisplayName("링크를 찾을 수 없다는 예외를 던진다")
            void it_returns_link_not_found_exception() {
                assertThrows(LinkNotFoundException.class, () -> linkService.updateLink(givenLinkID, updateSource));
            }
        }
    }

    @Nested
    @DisplayName("deleteLink()")
    class Describe_deleteLink {

        @Nested
        @DisplayName("존재하는 링크 id가 주어진다면")
        class Context_exist_link_id {
            Long givenLinkID = LINK.getId();

            @BeforeEach
            void setUp() {
                linkService.createLink(LINK);
            }

            @Test
            @DisplayName("주어진 id와 일치하는 링크를 삭제한 뒤 반환한다")
            void it_return_deleted_link() {
                Link link = linkService.deleteLink(givenLinkID);

                assertThat(link.getTitle()).isEqualTo(LINK.getTitle());
                assertThat(link.getLinkURL()).isEqualTo(LINK.getLinkURL());
                assertThat(link.getCategory()).isEqualTo(LINK.getCategory());
                assertThat(link.getType()).isEqualTo(LINK.getType());
                assertThat(link.getDescription()).isEqualTo(LINK.getDescription());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 링크 id가 주어진다면")
        class Context_not_exist_link_id {
            Long givenLinkID = NOT_EXIST_ID;

            @Test
            @DisplayName("링크를 찾을 수 없다는 예외를 던진다")
            void it_returns_link_not_found_exception() {
                assertThrows(LinkNotFoundException.class, () -> linkService.deleteLink(givenLinkID));
            }
        }
    }

}
