package com.example.finalexam.constants;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateFormatConstants {
    public static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("M/d/yyyy"), //US short
            DateTimeFormatter.ofPattern("yyyy-MM-dd"), // ISO
            DateTimeFormatter.ofPattern("dd/MM/yyyy"), // European short
            DateTimeFormatter.ofPattern("d-MMM-yyyy"), //European verbose
            DateTimeFormatter.ofPattern("MM-dd-yyyy"), //Another US format
            DateTimeFormatter.ofPattern("yyyyMMdd") // No separator
    );

    private DateFormatConstants() {
        //Prevent instantiation
    }
}
