package com.jinseong.soft.modules.category.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void testSaveAndGetCategory() {
        Category source = Category.builder()
                .title("개발")
                .build();

        Category category = categoryRepository.save(source);
        Optional<Category> findResult =
                categoryRepository.findByTitle("개발");

        assertThat(findResult).isNotEmpty();
        assertThat(findResult.get().getId()).isEqualTo(category.getId());
        assertThat(findResult.get().getTitle()).isEqualTo(category.getTitle());
    }
}