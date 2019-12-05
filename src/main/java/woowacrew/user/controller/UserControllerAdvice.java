package woowacrew.user.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.user.domain.exception.DegreeBoundException;
import woowacrew.user.domain.exception.ForbiddenUserException;
import woowacrew.user.service.exception.InvalidBirthdayException;
import woowacrew.user.service.exception.NotExistUserException;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(InvalidBirthdayException.class)
    public RedirectView exceptBirthday(InvalidBirthdayException exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());

        return new RedirectView("/users/form");
    }

    @ExceptionHandler({NotExistUserException.class, ForbiddenUserException.class, DegreeBoundException.class})
    public RedirectView exceptUserRoleUpdate(Exception exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());

        return new RedirectView("/error");
    }
}
