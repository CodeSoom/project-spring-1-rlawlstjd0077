package com.jinseong.soft.application;

import com.jinseong.soft.domain.Category;
import com.jinseong.soft.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
