package com.jinseong.soft.domain;

import java.util.Optional;

/**
 * 좋아요 저장소.
 */
public interface LikeRepository {
    /**
     * 주어진 좋아요를 저장하고 반환합니다.
     *
     * @param like 좋아요
     * @return 저장된 좋아요
     */
    Like save(Like like);

    /**
     * 주어진 유저와 링크에 대해 저장된 좋아요를 반환합니다.
     *
     * @param user 유저
     * @param link 링크
     * @return 좋아요
     */
    Optional<Like> findByUserAndLink(User user, Link link);
}
