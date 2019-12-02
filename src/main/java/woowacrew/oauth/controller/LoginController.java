package woowacrew.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import woowacrew.oauth.Oauth;
import woowacrew.oauth.service.LoginService;
import woowacrew.user.domain.User;
import woowacrew.utils.annotation.AuthenticationUser;

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
    public String login() {
        return "login";
    }

    @GetMapping("/userPage")
    @PreAuthorize("hasRole('USER')")
    public String user(@AuthenticationUser User user) {
        return "userPage";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "admin";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> hello(@AuthenticationUser User user) {
        return ResponseEntity.ok(user);
    }
}
