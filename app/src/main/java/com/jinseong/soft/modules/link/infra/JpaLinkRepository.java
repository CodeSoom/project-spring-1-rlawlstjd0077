package com.jinseong.soft.modules.link.infra;

import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.link.domain.LinkRepository;
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
