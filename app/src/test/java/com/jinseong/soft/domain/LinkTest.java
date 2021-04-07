package com.jinseong.soft.domain;

import com.jinseong.soft.LinkTestFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinkTest {
    private final Link link = LinkTestFixture.LINK;

    @Test
    void createLinkWithBuilder() {
        assertThat(link.getTitle()).isEqualTo("스프링 부트 문서");
        assertThat(link.getLinkURL())
                .isEqualTo("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/");
        assertThat(link.getCategory()).isEqualTo("Spring");
        assertThat(link.getType()).isEqualTo("Document");
        assertThat(link.getDescription()).isEqualTo("스프링 부트에 관한 공식 문서이다.");
    }

    @Test
    void updateLinkWithAnotherLink() {
        link.changeWith(Link.builder()
                .title("SpringBoot Document")
                .linkURL("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/")
                .category("SpringBoot")
                .type("Document")
                .description("This is the official documentation for Spring Boot.")
                .build()
        );

        assertThat(link.getTitle()).isEqualTo("SpringBoot Document");
        assertThat(link.getLinkURL())
                .isEqualTo("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/");
        assertThat(link.getCategory()).isEqualTo("SpringBoot");
        assertThat(link.getType()).isEqualTo("Document");
        assertThat(link.getDescription()).isEqualTo("This is the official documentation for Spring Boot.");
    }
}
