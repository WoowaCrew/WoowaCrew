package woowacrew.user.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.user.domain.UserContext;
import woowacrew.user.service.exception.InvalidBirthdayException;
import woowacrew.utils.annotation.AuthenticationUser;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(InvalidBirthdayException.class)
    public RedirectView exceptBirthday(InvalidBirthdayException exception, RedirectAttributes redirectAttributes, @AuthenticationUser UserContext userContext) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());

        return new RedirectView("/users/" + userContext.getId() + "/form");
    }
}
