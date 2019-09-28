package com.evolve.server.converters;

import com.evolve.model.AcceptStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AcceptStatusEnumConverter implements Converter<String, AcceptStatus> {
    @Override
    public AcceptStatus convert(String value) {
        return AcceptStatus.of(Integer.parseInt(value));
    }
}
