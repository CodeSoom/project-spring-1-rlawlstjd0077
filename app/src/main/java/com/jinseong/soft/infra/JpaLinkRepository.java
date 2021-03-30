package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.LinkRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaLinkRepository
        extends LinkRepository, CrudRepository<Link, Long> {
    List<Link> findAll();

    Optional<Link> findById(Long id);

    Link save(Link Link);

    void delete(Link Link);
}
