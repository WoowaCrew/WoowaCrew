package woowacrew.oauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacrew.security.SecurityContextSupport;
import woowacrew.user.dto.UserContext;

@RestController
public class LoginApiController {
    @GetMapping("/login/info")
    public ResponseEntity<UserContext> userInfo() {
        if (SecurityContextSupport.isNotLogined()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(SecurityContextSupport.getUserContext());
    }
}
