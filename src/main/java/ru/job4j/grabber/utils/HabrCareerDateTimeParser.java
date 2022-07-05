package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class HabrCareerDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        ZonedDateTime time = ZonedDateTime.parse(parse);
        LocalDateTime date = time.toLocalDateTime();
        return date;
    }
}