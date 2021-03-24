package com.jinseong.soft.application;

import com.jinseong.soft.domain.Link;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinkServiceTest {

    private LinkService linkService = new LinkService();

    @Nested
    @DisplayName("getLinks()")
    class Describe_getLinks {

        @Nested
        @DisplayName("링크가 존재하지 않을 때")
        class Context_with_no_link {
            @Test
            @DisplayName("비어있는 목록을 반환한다.")
            void It_returns_empty_list() {
                List<Link> linkList = linkService.getLinks();

                assertThat(linkList).isEmpty();
            }
        }
    }
}
