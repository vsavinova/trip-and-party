package com.evolve.server.converters;

import com.evolve.model.Visibility;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VisibilityEnumConverter implements Converter<String, Visibility> {
    @Override
    public Visibility convert(String value) {
        return Visibility.of(Integer.parseInt(value));
    }
}
