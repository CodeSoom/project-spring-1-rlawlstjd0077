package com.jinseong.soft.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 링크 정보
 */
@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Link extends DateAudit {
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
     * 링크 생성 유저
     */
    @OneToOne(fetch = FetchType.LAZY)
    @Column(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "link")
    private Set<Like> likes = new HashSet<>();

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

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public int getLikeCount() {
        return likes.size();
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }
}
