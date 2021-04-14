package com.jinseong.soft.modules.type.application;

import com.jinseong.soft.modules.type.domain.Type;
import com.jinseong.soft.modules.type.domain.TypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 링크 타입에 대한 비즈니스 로직을 제공합니다.
 */
@Service
@Transactional
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /**
     * 주어진 타입명과 일치하는 타입를 반환하고 존재하지 않는 경우 타입를 생성한 뒤 반환합니다.
     *
     * @param title 타입명
     * @return 타입(존재하는 경우), 생성된 타입 (그렇지 않은 경우)
     */
    public Type getOrCreateType(String title) {
        Optional<Type> findByTitle = typeRepository.findByTitle(title);
        return findByTitle.orElseGet(() -> createNewType(title));
    }

    /**
     * 주어진 타입명으로 타입를 생성한 뒤 반환합니다.
     *
     * @param title 타입명
     * @return 생성된 타입
     */
    private Type createNewType(String title) {
        Type type = Type.builder()
                .title(title)
                .build();
        return typeRepository.save(type);
    }
}
