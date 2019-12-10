package woowacrew.user.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserResponseDto;
import woowacrew.user.dto.UserUpdateDto;
import woowacrew.user.service.UserService;
import woowacrew.utils.annotation.AuthenticationUser;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/form")
    public ModelAndView updateForm(@AuthenticationUser UserContext userContext, ModelAndView modelAndView) {
        Long id = userContext.getId();
        UserResponseDto user = userService.findById(id);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user-update-form");

        logger.info("User update form enter : {}", id);
        return modelAndView;
    }

    @PostMapping("/users/update")
    public RedirectView update(@AuthenticationUser UserContext userContext, UserUpdateDto userUpdateDto) {
        UserResponseDto user = userService.update(userContext.getId(), userUpdateDto);
        SecurityContextSupport.updateContext(new ModelMapper().map(user, UserContext.class));

        logger.info("Update user : {}", user);
        return new RedirectView("/");
    }
}
