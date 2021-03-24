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
     * 링크의 type (Youtube, Blog, Book, Resource, Document)
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

    /**
     * 주어진 source로 부터 링크의 정보를 업데이트 합니다.
     *
     * @param source 링크 수정 정보
     */
    public void changeWith(Link source) {
        this.title = source.getTitle();
        this.linkURL = source.getLinkURL();
        this.description = source.getDescription();
        this.type = source.getType();
        this.category = source.getCategory();
    }
}