package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Type;
import com.jinseong.soft.domain.TypeRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaTypeRepository
        extends TypeRepository, CrudRepository<Type, Long> {
    Optional<Type> findByTitle(String title);
    
    Type save(Type type);
}
