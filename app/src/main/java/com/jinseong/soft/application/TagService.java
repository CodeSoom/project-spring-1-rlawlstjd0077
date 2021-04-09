package com.jinseong.soft.application;

import com.jinseong.soft.domain.Tag;
import com.jinseong.soft.domain.TagRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getOrCreateTag(String title) {
        Optional<Tag> findByTitle = tagRepository.findByTitle(title);
        return findByTitle.orElseGet(() -> createNewTag(title));
    }

    private Tag createNewTag(String title) {
        Tag tag = Tag.builder()
                .title(title)
                .build();
        return tagRepository.save(tag);
    }
}
