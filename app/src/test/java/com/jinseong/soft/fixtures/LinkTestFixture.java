package com.jinseong.soft.fixtures;

import com.jinseong.soft.modules.category.domain.Category;
import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.link.dto.LinkRequestData;
import com.jinseong.soft.modules.tag.domain.Tag;
import com.jinseong.soft.modules.type.domain.Type;

import java.util.Collections;
import java.util.HashSet;

public class LinkTestFixture {
    public static final Category CATEGORY = Category.builder()
            .title("Spring")
            .build();

    public static final Category UPDATE_CATEGORY = Category.builder()
            .title("SpringBoot")
            .build();

    public static final Type TYPE = Type.builder()
            .title("Youtube")
            .build();

    public static final Type UPDATE_TYPE = Type.builder()
            .title("Document")
            .build();

    public static final Tag TAG = Tag.builder()
            .title("Spring")
            .build();

    public static final Tag UPDATE_TAG = Tag.builder()
            .title("Spring")
            .build();

    public static final long EXIST_ID = 0L;
    public static final long NOT_EXIST_ID = 10000L;

    public static final LinkRequestData LINK_REQUEST = LinkRequestData.builder()
            .title("스프링 부트 문서")
            .linkURL("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/")
            .category(CATEGORY.getTitle())
            .type(TYPE.getTitle())
            .description("스프링 부트에 관한 공식 문서이다.")
            .tags(Collections.singletonList(TAG.getTitle()))
            .build();

    public static final LinkRequestData UPDATE_LINK_REQUEST = LinkRequestData.builder()
            .title("SpringBoot Document")
            .linkURL("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/")
            .category(UPDATE_CATEGORY.getTitle())
            .type(UPDATE_TYPE.getTitle())
            .description("This is the official documentation for Spring Boot.")
            .tags(Collections.singletonList(UPDATE_TAG.getTitle()))
            .build();

    public static Link generateLink() {
        return Link.builder()
                .id(EXIST_ID)
                .title(LINK_REQUEST.getTitle())
                .description(LINK_REQUEST.getDescription())
                .linkURL(LINK_REQUEST.getLinkURL())
                .type(TYPE)
                .category(CATEGORY)
                .tags(Collections.singleton(TAG))
                .createdUser(UserTestFixture.EXIST_USER)
                .likes(new HashSet<>())
                .build();
    }

    public static Link generateUpdateLink() {
        return Link.builder()
                .id(EXIST_ID)
                .title(UPDATE_LINK_REQUEST.getTitle())
                .description(UPDATE_LINK_REQUEST.getDescription())
                .linkURL(UPDATE_LINK_REQUEST.getLinkURL())
                .type(UPDATE_TYPE)
                .category(UPDATE_CATEGORY)
                .tags(Collections.singleton(UPDATE_TAG))
                .createdUser(UserTestFixture.EXIST_USER)
                .likes(new HashSet<>())
                .build();
    }
}
