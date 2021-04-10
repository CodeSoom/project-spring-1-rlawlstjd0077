package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Like;
import com.jinseong.soft.domain.LikeRepository;
import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaLikeRepository
        extends LikeRepository, CrudRepository<Like, Long> {
    Like save(Like like);

    Optional<Like> findByUserAndLink(User user, Link link);
}
