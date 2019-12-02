package woowacrew.user.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import woowacrew.user.service.exception.InvalidBirthdayException;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(InvalidBirthdayException.class)
    @ResponseBody
    public ErrorMessage exceptBirthday(InvalidBirthdayException exception) {
        return ErrorMessage.badRequest(exception);
    }
}
