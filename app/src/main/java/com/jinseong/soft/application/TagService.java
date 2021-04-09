package com.jinseong.soft.application;

import com.jinseong.soft.domain.Tag;
import com.jinseong.soft.domain.TagRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getOrCreateTag(String title) {
        Optional<Tag> byTitle = tagRepository.findByTitle(title);
        return byTitle.orElseGet(() -> createNewTag(title));
    }

    private Tag createNewTag(String title) {
        Tag tag = Tag.builder()
                .title(title)
                .build();
        return tagRepository.save(tag);
    }
}
