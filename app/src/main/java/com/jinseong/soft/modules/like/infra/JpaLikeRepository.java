package com.jinseong.soft.modules.like.infra;

import com.jinseong.soft.modules.like.domain.Like;
import com.jinseong.soft.modules.like.domain.LikeRepository;
import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaLikeRepository
        extends LikeRepository, CrudRepository<Like, Long> {
    Like save(Like like);

    Optional<Like> findByUserAndLink(User user, Link link);
}
