package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Tag;
import com.jinseong.soft.domain.TagRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaTagRepository
        extends TagRepository, CrudRepository<Tag, Long> {
    List<Tag> findAll();

    Optional<Tag> findByTitle(String title);

    Tag save(Tag tag);
}
