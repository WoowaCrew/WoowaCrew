package woowacrew.user.utils;

import woowacrew.user.service.exception.InvalidBirthdayException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BirthdayConverter {
    private BirthdayConverter() {
    }

    public static LocalDate toEntity(String rawBirthday) {
        try {
            LocalDate birthday = LocalDate.parse(rawBirthday);
            validBirthday(birthday);
            return birthday;
        } catch (IllegalArgumentException | DateTimeParseException e) {
            throw new InvalidBirthdayException(e);
        }
    }

    private static void validBirthday(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("생일 입력이 올바르지 않습니다.");
        }
    }
}
