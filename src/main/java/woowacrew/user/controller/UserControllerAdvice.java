package woowacrew.user.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.user.domain.UserResponseDto;
import woowacrew.user.service.exception.InvalidBirthdayException;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(InvalidBirthdayException.class)
    public RedirectView exceptBirthday(InvalidBirthdayException exception, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        UserResponseDto user = (UserResponseDto) httpSession.getAttribute("user");
        redirectAttributes.addFlashAttribute("error", exception.getMessage());

        return new RedirectView("/users/" + user.getId() + "/form");
    }
}
