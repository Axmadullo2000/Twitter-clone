package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {
    public static String postedTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return now.format(formatter);
    }
}
