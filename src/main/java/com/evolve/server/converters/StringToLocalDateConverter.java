package com.evolve.server.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    public LocalDate convert(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        return LocalDate.parse(source, formatter);
    }
}
