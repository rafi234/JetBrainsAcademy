package platform.myUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyUtils {
    public static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";

    public static String getFormattedDate(String format, LocalDateTime date) {
        return DateTimeFormatter.ofPattern(format).format(date);
    }
}
