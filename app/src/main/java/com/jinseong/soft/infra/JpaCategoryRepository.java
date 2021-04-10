package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Category;
import com.jinseong.soft.domain.CategoryRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCategoryRepository
        extends CategoryRepository, CrudRepository<Category, Long> {
    List<Category> findAll();

    Optional<Category> findByTitle(String title);

    Category save(Category category);
}
