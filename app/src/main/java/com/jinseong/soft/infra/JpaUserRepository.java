package com.jinseong.soft.infra;

import com.jinseong.soft.domain.User;
import com.jinseong.soft.domain.UserRepository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {
    User save(User user);

    Optional<User> findByIdAndDeletedIsFalse(Long id);
    
    boolean existsByEmail(String email);
}
