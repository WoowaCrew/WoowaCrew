package woowacrew.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
    private static final String GITHUB_AUTH_PAGE = "https://github.com/login/oauth/authorize?";
    private static final String CLIENT_ID = "client_id=013cc23d4922a77a88b9";

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView(GITHUB_AUTH_PAGE + CLIENT_ID);
    }

    @GetMapping("/oauth/github")
    public RedirectView oauth(@RequestParam String code) {
        
        return new RedirectView("/");
    }

}
