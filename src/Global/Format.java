package Global;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Format {
    public static LocalDateTime formatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, formatter);
    }
}
