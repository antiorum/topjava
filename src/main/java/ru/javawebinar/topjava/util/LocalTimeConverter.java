package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

public class LocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        try {
            int hours = Integer.parseInt(source.substring(0, 2));
            int minutes = Integer.parseInt(source.substring(source.length() - 2));
            return LocalTime.of(hours, minutes);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }
}
