package com.jinseong.soft.domain;

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
}
