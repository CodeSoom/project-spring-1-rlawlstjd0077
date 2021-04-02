package com.jinseong.soft.domain;

import java.util.Optional;

/**
 * 유저 저장소.
 */
public interface UserRepository {
    /**
     * 주어진 유저를 저장하고 반환합니다.
     *
     * @param user 유저
     * @return 저장된 유저
     */
    User save(User user);

    /**
     * 삭제되지 않은 유저중 전달받은 식별자와 일치하는 유저를 반환합니다.
     *
     * @param id 유저 식별자
     * @return 유저
     */
    Optional<User> findByIdAndDeletedIsFalse(Long id);
}
