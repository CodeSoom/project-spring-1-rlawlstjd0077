package com.jinseong.soft;

import com.jinseong.soft.domain.Category;
import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.Tag;
import com.jinseong.soft.domain.Type;
import com.jinseong.soft.dto.LinkData;

import java.util.Collections;

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

    public static final LinkData LINK_REQUEST = LinkData.builder()
            .title("스프링 부트 문서")
            .linkURL("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/")
            .category(CATEGORY.getTitle())
            .type(TYPE.getTitle())
            .description("스프링 부트에 관한 공식 문서이다.")
            .tags(Collections.singletonList(TAG.getTitle()))
            .build();

    public static final LinkData UPDATE_LINK_REQUEST = LinkData.builder()
            .title("SpringBoot Document")
            .linkURL("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/")
            .category(UPDATE_CATEGORY.getTitle())
            .type(UPDATE_TYPE.getTitle())
            .description("This is the official documentation for Spring Boot.")
            .tags(Collections.singletonList(UPDATE_TAG.getTitle()))
            .build();

    public static Link generateLink() {
        return Link.builder()
                .id(LinkTestFixture.EXIST_ID)
                .title(LINK_REQUEST.getTitle())
                .description(LINK_REQUEST.getDescription())
                .linkURL(LINK_REQUEST.getLinkURL())
                .type(LinkTestFixture.TYPE)
                .category(LinkTestFixture.CATEGORY)
                .tags(Collections.singleton(TAG))
                .build();
    }
}
