package woowacrew.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.oauth.Oauth;
import woowacrew.oauth.service.LoginService;
import woowacrew.user.domain.UserDto;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {
    public static final String USER = "user";
    public static final String ACCESS_TOKEN = "accessToken";

    private final Oauth oauth;
    private final LoginService loginService;

    @Autowired
    public LoginController(Oauth oauth, LoginService loginService) {
        this.oauth = oauth;
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public RedirectView login(HttpSession session) {
        if (Objects.nonNull(session.getAttribute(USER))) {
            return new RedirectView("/");
        }

        return new RedirectView(oauth.getUserAuthorizationUri());
    }

    @GetMapping("/oauth/github")
    public RedirectView oauth(HttpSession session, @RequestParam String code) {
        String accessToken = oauth.getAccessToken(code);
        UserDto user = oauth.getUserInfo(accessToken);
        loginService.save(user);

        session.setAttribute(ACCESS_TOKEN, accessToken);
        session.setAttribute(USER, user);
        return new RedirectView("/");
    }
}
