package mk.ukim.finki.dick.prezemiakcijabackend.domain.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {

    public static LocalDateTime convertFromStringToDateAndTime(String dateAndTime) {
        return LocalDateTime.parse(dateAndTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
