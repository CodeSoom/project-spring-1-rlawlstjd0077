package com.jinseong.soft.modules.category.application;

import com.jinseong.soft.modules.category.domain.Category;
import com.jinseong.soft.modules.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 링크 카테고리에 대한 비즈니스 로직을 제공합니다.
 */
@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * 주어진 카테고리명과 일치하는 카테고리를 반환하고 존재하지 않는 경우 카테고리를 생성한 뒤 반환합니다.
     *
     * @param title 카테고리명
     * @return 카테고리(존재하는 경우), 생성된 카테고리 (그렇지 않은 경우)
     */
    public Category getOrCreateCategory(String title) {
        Optional<Category> findByTitle = categoryRepository.findByTitle(title);
        return findByTitle.orElseGet(() -> createNewCategory(title));
    }

    /**
     * 주어진 카테고리명으로 카테고리를 생성한 뒤 반환합니다.
     *
     * @param title 카테고리명
     * @return 생성된 카테고리
     */
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
