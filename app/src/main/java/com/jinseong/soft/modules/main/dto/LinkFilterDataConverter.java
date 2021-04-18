package com.jinseong.soft.modules.main.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LinkFilterDataConverter implements Converter<String, Optional<LinkFilterData>> {
    private final ObjectMapper objectMapper;

    public LinkFilterDataConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<LinkFilterData> convert(String value) {
        try {
            LinkFilterData linkFilterData = new ObjectMapper().readValue(value, LinkFilterData.class);
            return Optional.of(linkFilterData);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
