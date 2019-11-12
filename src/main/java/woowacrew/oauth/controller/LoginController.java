package woowacrew.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.oauth.Oauth;
import woowacrew.user.domain.UserDto;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final Oauth oauth;

    public LoginController(Oauth oauth) {
        this.oauth = oauth;
    }

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView(oauth.getUserAuthorizationUri());
    }

    @GetMapping("/oauth/github")
    public RedirectView oauth(HttpSession session, @RequestParam String code) {
        String accessToken = oauth.getAccessToken(code);
        UserDto user = oauth.getUserInfo(accessToken);

        session.setAttribute("accessToken", accessToken);
        session.setAttribute("user", user);
        return new RedirectView("/");
    }
}
