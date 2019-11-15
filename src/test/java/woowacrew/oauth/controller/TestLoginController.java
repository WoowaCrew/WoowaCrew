package woowacrew.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import woowacrew.user.domain.UserDto;

import javax.servlet.http.HttpSession;

@Controller
public class TestLoginController {
    @PostMapping("/test/login")
    public String testLogin(HttpSession session) {
        session.setAttribute("user", new UserDto("test", "test"));
        return "index";
    }
}
