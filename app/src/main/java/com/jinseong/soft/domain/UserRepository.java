package com.jinseong.soft.domain;

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
}
