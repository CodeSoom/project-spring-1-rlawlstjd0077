package com.jinseong.soft.application;

import com.jinseong.soft.domain.Category;
import com.jinseong.soft.domain.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class CategoryServiceTest {
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);

        given(categoryRepository.findByTitle("Develop"))
                .willReturn(Optional.of(
                        Category.builder()
                                .id(0L)
                                .title("Develop")
                                .build()
                ));
    }

    @Test
    void createAndGetTag() {
        String title = "Develop";

        Category category = categoryService.getOrCreateCategory(title);
        assertThat(category.getTitle()).isEqualTo(title);
    }
}