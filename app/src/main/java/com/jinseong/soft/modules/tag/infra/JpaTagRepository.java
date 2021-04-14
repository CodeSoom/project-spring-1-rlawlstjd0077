package com.jinseong.soft.modules.tag.infra;

import com.jinseong.soft.modules.tag.domain.Tag;
import com.jinseong.soft.modules.tag.domain.TagRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaTagRepository
        extends TagRepository, CrudRepository<Tag, Long> {
    List<Tag> findAll();

    Optional<Tag> findByTitle(String title);

    Tag save(Tag tag);
}
