package com.jinseong.soft.modules.like.domain;

import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.user.domain.User;
import lombok.*;

import javax.persistence.*;

/**
 * 링크에 대한 좋아요 정보
 */
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Link link;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
