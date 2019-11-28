package woowacrew.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import woowacrew.user.domain.UserResponseDto;
import woowacrew.user.service.UserService;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}/form")
    public ModelAndView updateForm(@PathVariable Long id, ModelAndView modelAndView) {
        UserResponseDto user = userService.findById(id);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user-update-form");
        return modelAndView;
    }
}
