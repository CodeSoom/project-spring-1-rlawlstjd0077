package com.jinseong.soft.domain;

import java.util.Optional;

/**
 * 타입 저장소
 */
public interface TypeRepository {
    Optional<Type> findByTitle(String title);

    Type save(Type category);
}
