package com.jinseong.soft;

import com.jinseong.soft.domain.Link;

public class LinkTestFixture {
    public static final Link LINK = Link.builder()
            .id(0L)
            .title("스프링 부트 문서")
            .linkURL("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/")
            .category("Spring")
            .type("Document")
            .description("스프링 부트에 관한 공식 문서이다.")
            .build();

    public static final long EXIST_ID = 0L;
    public static final long NOT_EXIST_ID = 10000L;
    

}
