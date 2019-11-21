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
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/test/login")
    public String testLogin(HttpSession session) {
        User testUser = new User("test", "test");
        User user = userRepository.save(testUser);
        session.setAttribute("user", UserConverter.userToUserDto(user));
        return "index";
    }
}
