package woowacrew.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserConverter;
import woowacrew.user.domain.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
public class TestLoginController {
    private static final long TEST_USER_ID = 1L;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/test/login")
    public String testLogin(HttpSession session) {
        User user = userRepository.findById(TEST_USER_ID).get();
        session.setAttribute("user", UserConverter.userToUserResponseDto(user));
        return "index";
    }
}
