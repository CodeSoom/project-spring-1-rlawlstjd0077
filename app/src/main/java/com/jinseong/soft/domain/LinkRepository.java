package com.jinseong.soft.domain;

import java.util.List;
import java.util.Optional;

/**
 * 링크 저장소.
 */
public interface LinkRepository {
    /**
     * 저장소에 존재하는 모든 링크 목록을 반환합니다.
     *
     * @return 링크 목록
     */
    List<Link> findAll();

    /**
     * 대응되는 식별자의 링크를 반환합니다.
     *
     * @param id 링크 식별자
     * @return 대응되는 식별자의 링크
     */
    Optional<Link> findById(Long id);

    /**
     * 주어진 링크를 저장하고 반환합니다.
     *
     * @param link 링크
     * @return 저장된 링크
     */
    Link save(Link link);

    /**
     * 주어진 링크를 삭제합니다.
     *
     * @param link 삭제할 링크
     */
    void delete(Link link);
}
