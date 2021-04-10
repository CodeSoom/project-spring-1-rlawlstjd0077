package com.jinseong.soft.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 링크에 대한 좋아요 정보
 */
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Link link;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
