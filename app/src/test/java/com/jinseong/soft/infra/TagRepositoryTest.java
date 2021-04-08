package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Tag;
import com.jinseong.soft.domain.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    @Test
    void testSaveAndGetTag() {
        Tag source = Tag.builder()
                .title("Spring")
                .build();

        Tag tag = tagRepository.save(source);
        Optional<Tag> findResult =
                tagRepository.findByTitle("Spring");

        assertThat(findResult).isNotEmpty();
        assertThat(findResult.get().getId()).isEqualTo(tag.getId());
        assertThat(findResult.get().getTitle()).isEqualTo(tag.getTitle());
    }
}