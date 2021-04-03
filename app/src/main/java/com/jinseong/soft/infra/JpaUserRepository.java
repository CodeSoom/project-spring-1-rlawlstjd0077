package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.User;
import com.jinseong.soft.domain.UserRepository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository
        extends UserRepository, CrudRepository<Link, Long> {
    @Override
    User save(User user);

    @Override
    Optional<User> findByIdAndDeletedIsFalse(Long id);
}
