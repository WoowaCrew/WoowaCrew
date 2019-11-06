package woowacrew.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import woowacrew.oauth.github.GithubConfig;
import woowacrew.oauth.github.GithubOauth;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final GithubConfig githubConfig;
    private final GithubOauth githubOauth;

    public LoginController(GithubConfig githubConfig, GithubOauth githubOauth) {
        this.githubConfig = githubConfig;
        this.githubOauth = githubOauth;
    }

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView(githubConfig.getUserAuthorizationUri() + "?client_id=" + githubConfig.getClientId());
    }

    @GetMapping("/oauth/github")
    public RedirectView oauth(HttpSession session, @RequestParam String code) {
        String accessToken = githubOauth.getAccessToken(code);
        githubOauth.getUserInfo(accessToken);
        session.setAttribute("accessToken", accessToken);
        return new RedirectView("/");
    }
}
