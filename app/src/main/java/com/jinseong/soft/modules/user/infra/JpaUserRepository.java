package com.jinseong.soft.modules.user.infra;

import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.domain.UserRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {
    User save(User user);

    Optional<User> findByIdAndDeletedIsFalse(Long id);

    boolean existsByEmail(String email);
}
