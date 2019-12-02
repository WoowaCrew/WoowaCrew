package woowacrew.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.user.domain.UserResponseDto;
import woowacrew.user.domain.UserUpdateDto;
import woowacrew.user.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}/form")
    public ModelAndView updateForm(@PathVariable Long id, ModelAndView modelAndView) {
        UserResponseDto user = userService.findById(id);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user-update-form");

        logger.info("User update form enter : {}", id);
        return modelAndView;
    }

    @PostMapping("/users/{id}/update")
    public RedirectView update(@PathVariable Long id, UserUpdateDto userUpdateDto, HttpSession session) {
        UserResponseDto user = userService.update(id, userUpdateDto);
        session.setAttribute("user", user);

        logger.info("Update user : {}", user);
        return new RedirectView("/");
    }
}
