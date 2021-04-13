package com.jinseong.soft.modules.category.application;

import com.jinseong.soft.modules.category.domain.Category;
import com.jinseong.soft.modules.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getOrCreateCategory(String title) {
        Optional<Category> findByTitle = categoryRepository.findByTitle(title);
        return findByTitle.orElseGet(() -> createNewCategory(title));
    }

    private Category createNewCategory(String title) {
        Category category = Category.builder()
                .title(title)
                .build();
        return categoryRepository.save(category);
    }
}
