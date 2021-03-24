package com.jinseong.soft.application;

import com.jinseong.soft.domain.Link;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinkServiceTest {

    private LinkService linkService = new LinkService();
    private final Link link = Link.builder()
            .title("스프링 부트 문서")
            .linkURL("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/")
            .category("Spring")
            .type("Document")
            .description("스프링 부트에 관한 공식 문서이다.")
            .build();

    @Nested
    @DisplayName("getLinks()")
    class Describe_getLinks {

        @Nested
        @DisplayName("링크가 존재하지 않을 때")
        class Context_with_no_link {
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
                linkService.createLink(link);
            }

            @Test
            @DisplayName("존재하는 링크 목록을 반환한다")
            void It_returns_exist_link_list() {
                List<Link> linkList = linkService.getLinks();

                assertThat(linkList).isNotEmpty();
            }
        }
    }
}
