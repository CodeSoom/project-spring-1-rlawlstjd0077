package com.jinseong.soft.modules.tag.application;

import com.jinseong.soft.modules.tag.domain.Tag;
import com.jinseong.soft.modules.tag.domain.TagRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 링크 태그에 대한 비즈니스 로직을 제공합니다.
 */
@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * 주어진 태그명 일치하는 태그를 반환하고 존재하지 않는 경우 태그를 생성한 뒤 반환합니다.
     *
     * @param title 태그명
     * @return 태그(존재하는 경우), 생성된 태그 (그렇지 않은 경우)
     */
    public Tag getOrCreateTag(String title) {
        Optional<Tag> findByTitle = tagRepository.findByTitle(title);
        return findByTitle.orElseGet(() -> createNewTag(title));
    }

    /**
     * 주어진 태그명으로 태그를 생성한 뒤 반환합니다.
     *
     * @param title 태그명
     * @return 생성된 태그
     */
    private Tag createNewTag(String title) {
        Tag tag = Tag.builder()
                .title(title)
                .build();
        return tagRepository.save(tag);
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }
}
