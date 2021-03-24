package com.jinseong.soft.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 링크 정보
 */
@Entity
@Getter
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 링크 제목
     */
    private String title;

    /**
     * 링크 URL
     */
    private String linkURL;

    /**
     * 링크 설명
     */
    private String description;

    /**
     * 링크 카테고리 (Java, DB, Spring)
     */
    //TODO: 추후 목록으로 변경 필요
    private String category;

    /**
     * 링크의 type (Youtube, Blog, Book, Resource)
     */
    //TODO: Enum 으로 변경 필요
    private String type;

    @Builder
    public Link(String title, String linkURL, String description, String category, String type) {
        this.title = title;
        this.linkURL = linkURL;
        this.description = description;
        this.category = category;
        this.type = type;
    }
}
