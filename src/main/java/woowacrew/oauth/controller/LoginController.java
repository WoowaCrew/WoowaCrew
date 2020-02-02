package woowacrew.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.oauth.OauthService;

@Controller
public class LoginController {
    private final OauthService oauthService;

    public LoginController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/login/github")
    public RedirectView loginWithGithub() {
        String authorizationUrl = oauthService.getAuthorizationUrl();
        return new RedirectView(authorizationUrl);
    }
}
