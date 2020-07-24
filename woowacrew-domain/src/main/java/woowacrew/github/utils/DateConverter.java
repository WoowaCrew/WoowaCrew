package woowacrew.github.utils;

import java.time.LocalDate;

public class DateConverter {

    private static final int FIRST_DAY = 1;

    private DateConverter() {
    }

    public static LocalDate toFirstDay(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonthValue(), FIRST_DAY);
    }
}
