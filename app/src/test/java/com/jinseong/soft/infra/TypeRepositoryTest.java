package com.jinseong.soft.infra;

import com.jinseong.soft.domain.Type;
import com.jinseong.soft.domain.TypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TypeRepositoryTest {

    @Autowired
    TypeRepository typeRepository;

    @Test
    void testSaveAndGetType() {
        Type source = Type.builder()
                .title("Youtube")
                .build();

        Type type = typeRepository.save(source);
        Optional<Type> findResult =
                typeRepository.findByTitle("Youtube");

        assertThat(findResult).isNotEmpty();
        assertThat(findResult.get().getId()).isEqualTo(type.getId());
        assertThat(findResult.get().getTitle()).isEqualTo(type.getTitle());
    }

}