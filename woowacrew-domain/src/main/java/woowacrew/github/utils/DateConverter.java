package woowacrew.github.utils;

import java.time.LocalDate;

public class DateConverter {

    private DateConverter() {
    }

    public static LocalDate toFirstDay(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonthValue(), 1);
    }
}
