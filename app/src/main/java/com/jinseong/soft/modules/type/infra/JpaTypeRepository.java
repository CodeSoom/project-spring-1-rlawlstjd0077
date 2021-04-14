package com.jinseong.soft.modules.type.infra;

import com.jinseong.soft.modules.type.domain.Type;
import com.jinseong.soft.modules.type.domain.TypeRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaTypeRepository
        extends TypeRepository, CrudRepository<Type, Long> {
    List<Type> findAll();

    Optional<Type> findByTitle(String title);

    Type save(Type type);
}
