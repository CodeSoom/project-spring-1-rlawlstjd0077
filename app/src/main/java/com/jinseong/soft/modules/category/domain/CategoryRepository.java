package com.jinseong.soft.modules.category.domain;

import java.util.List;
import java.util.Optional;

/**
 * 카테고리 저장소
 */
public interface CategoryRepository {
    /**
     * 저장된 모든 카테고리 목록을 반환합니다.
     *
     * @return 카테고리 목록
     */
    List<Category> findAll();

    /**
     * 주어진 카테고리명과 일치하는 카테고리를 반환합니다.
     *
     * @param title 카테고리명
     * @return 카테고리
     */
    Optional<Category> findByTitle(String title);

    /**
     * 주어진 카테고리를 저장하고 반환합니다.
     *
     * @param category 카테고리
     * @return 저장된 카테고리
     */
    Category save(Category category);
}
