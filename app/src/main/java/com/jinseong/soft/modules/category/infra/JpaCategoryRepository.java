package com.jinseong.soft.modules.category.infra;

import com.jinseong.soft.modules.category.domain.Category;
import com.jinseong.soft.modules.category.domain.CategoryRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * 카테고리 JPA 저장소.
 */
public interface JpaCategoryRepository
        extends CategoryRepository, CrudRepository<Category, Long> {
    List<Category> findAll();

    Optional<Category> findByTitle(String title);

    Category save(Category category);
}
