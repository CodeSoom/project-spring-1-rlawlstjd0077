package com.jinseong.soft.modules.tag.application;

import com.jinseong.soft.modules.tag.application.TagService;
import com.jinseong.soft.modules.tag.domain.Tag;
import com.jinseong.soft.modules.tag.domain.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class TagServiceTest {
    TagService tagService;

    @BeforeEach
    void setUp() {
        TagRepository tagRepository = Mockito.mock(TagRepository.class);
        tagService = new TagService(tagRepository);

        given(tagRepository.findByTitle("Spring"))
                .willReturn(Optional.of(
                        Tag.builder()
                                .id(0L)
                                .title("Spring")
                                .build()
                ));
    }

    @Test
    void createAndGetTag() {
        String title = "Spring";

        Tag tag = tagService.getOrCreateTag(title);
        assertThat(tag.getTitle()).isEqualTo(title);
    }
}
