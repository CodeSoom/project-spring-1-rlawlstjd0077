package com.jinseong.soft.modules.type.application;

import com.jinseong.soft.modules.type.domain.Type;
import com.jinseong.soft.modules.type.domain.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class TypeServiceTest {
    TypeService typeService;

    @BeforeEach
    void setUp() {
        TypeRepository typeRepository = Mockito.mock(TypeRepository.class);
        typeService = new TypeService(typeRepository);

        given(typeRepository.findByTitle("Youtube"))
                .willReturn(Optional.of(
                        Type.builder()
                                .id(0L)
                                .title("Youtube")
                                .build()
                ));
    }

    @Test
    void createAndGetTag() {
        String title = "Youtube";

        Type type = typeService.getOrCreateType(title);
        assertThat(type.getTitle()).isEqualTo(title);
    }
}