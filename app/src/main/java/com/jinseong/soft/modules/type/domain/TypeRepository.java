package com.jinseong.soft.modules.type.domain;

import java.util.List;
import java.util.Optional;

/**
 * 타입 저장소
 */
public interface TypeRepository {
    List<Type> findAll();

    Optional<Type> findByTitle(String title);

    Type save(Type category);
}
