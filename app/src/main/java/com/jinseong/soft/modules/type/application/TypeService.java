package com.jinseong.soft.modules.type.application;

import com.jinseong.soft.modules.type.domain.Type;
import com.jinseong.soft.modules.type.domain.TypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public Type getOrCreateType(String title) {
        Optional<Type> findByTitle = typeRepository.findByTitle(title);
        return findByTitle.orElseGet(() -> createNewType(title));
    }

    private Type createNewType(String title) {
        Type type = Type.builder()
                .title(title)
                .build();
        return typeRepository.save(type);
    }
}
