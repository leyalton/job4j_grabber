package ru.job4j.html;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Date {
    public static LocalDate parse(String date) {
        Map<String, Integer> MONTH = new HashMap<>() {
            {
                put("янв", 1);
                put("фев", 2);
                put("мар", 3);
                put("апр", 4);
                put("май", 5);
                put("июн", 6);
                put("июл", 7);
                put("авг", 8);
                put("сен", 9);
                put("окт", 10);
                put("ноя", 11);
                put("дек", 12);
            }
        };
        String[] split = date.split(" ");
        if (split.length == 2) {
            return date.contains("сегодня") ? LocalDate.now() : LocalDate.now().minusDays(1);
        } else {
            String[] parts = date.split(" ");
            int day = Integer.parseInt(parts[0]);
            int month = MONTH.get(parts[1]);
            int year = 2000 + Integer.parseInt(parts[2].substring(0, 2));
            return LocalDate.of(year, month, day);
        }
    }
}