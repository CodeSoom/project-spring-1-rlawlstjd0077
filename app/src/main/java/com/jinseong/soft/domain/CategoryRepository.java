package com.jinseong.soft.domain;

import java.util.List;
import java.util.Optional;

/**
 * 카테고리 저장소
 */
public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findByTitle(String title);

    Category save(Category category);
}
