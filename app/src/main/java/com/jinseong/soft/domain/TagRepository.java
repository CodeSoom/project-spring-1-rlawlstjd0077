package com.jinseong.soft.domain;

import java.util.List;
import java.util.Optional;

/**
 * 태그 저장소
 */
public interface TagRepository {
    List<Tag> findAll();

    Optional<Tag> findByTitle(String title);

    Tag save(Tag category);
}
