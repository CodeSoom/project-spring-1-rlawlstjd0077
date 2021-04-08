package com.jinseong.soft.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 링크 정보
 */
@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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
    @OneToOne
    private Category category;

    /**
     * 링크의 type (Youtube, Blog, Book, Resource, Document)
     */
    @OneToOne
    private Type type;

    /**
     * 링크의 tag (Spring, Java, MySQL)
     */
    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

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

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addTag(List<Tag> tag) {
        this.tags.addAll(tag);
    }
}
